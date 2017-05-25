$(document).ready(function() {

	function ChatViewModel() {

		var that = this;

		that.userName = ko.observable('');
		that.chatContent = ko.observable('');
		that.message = ko.observable('');
		that.messageIndex = ko.observable(0);
		that.chatId = ko.observable(null);
		that.activePollingXhr = ko.observable(null);
		
		var keepPolling = false;

		that.joinChat = function() {
			if (that.userName().trim() != '') {
				keepPolling = true;
				pollForMessages();
			}
		}

		function pollForMessages() {
			if (!keepPolling) {
				return;
			}
			
			var form = $("#joinChatForm");
			that.activePollingXhr($.ajax({url : form.attr("action"), type : "GET", data : form.serialize(),cache: false,
				success : function(messages) {
					for ( var i = 0; i < messages.length; i++) {
						that.chatContent(that.chatContent() + messages[i] + "\n");
						that.messageIndex(that.messageIndex() + 1);
					}
				},
				error : function(xhr) {
					if (xhr.statusText != "abort" && xhr.status != 503) {
						resetUI();
						console.error("Unable to retrieve chat messages. Chat ended.");
					}
				},
				complete : pollForMessages
			}));
			$('#message').focus();
		}

		that.postMessage = function() {
			if (that.message().trim() != '') {
				var form = $("#postMessageForm");
				$.ajax({url : form.attr("action"), type : "POST",
					  data : "message=" + $("#postMessageForm input[name=message]").val() + "&user=" + that.userName(),
					error : function(xhr) {
						console.error("Error posting chat message: status=" + xhr.status + ", statusText=" + xhr.statusText);
					}
				});
				that.message('');
			}
		}

		that.leaveChat = function() {
			that.activePollingXhr(null);
			resetUI();
			this.userName('');
		}

		function resetUI() {
			keepPolling = false;
			that.activePollingXhr(null);
			that.message('');
			that.messageIndex(0);
			that.chatContent('');
			location.reload();
		}
	}

	ko.applyBindings(new ChatViewModel());
});

