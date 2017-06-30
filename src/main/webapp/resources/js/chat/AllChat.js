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

			that.activePollingXhr(
			$.ajax({
			url : "chat",
            contentType : 'application/json; charset=utf-8',
            dataType : 'json',
			type : "GET",
			cache: false,
				success : function(messages) {
                    var text = "";

                    for(var i = 0; i < messages.length; i++)
                        text += messages[i] + "\n";

                    that.chatContent(text);
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
             var chat = {
                "id" : that.chatId(),
                "message" : that.message(),
                "user" : that.userName()
               }
				$.ajax(	{
				url : "chat",
				type : "POST",
                contentType : 'application/json; charset=utf-8',
                dataType : 'json',
                data: JSON.stringify(chat),
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

