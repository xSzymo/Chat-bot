/*function copyToClipboard(text) {
  window.prompt("Copy to clipboard: Ctrl+C, Enter", text);
}*/

$(document).ready(function() {

	function ChatViewModel() {

		var that = this;

		that.userName = ko.observable('');
		that.chatContent = ko.observable('');
		that.message = ko.observable('');
		that.messages = ko.observable([]);
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

		if(that.chatId() == null) {
               $.ajax({
                 type: "GET",
                 url: "chatWithUser/createChatId",
                 success: function(result) {
                 that.chatId(result.id);
                }});
           	}

             var chatId = {
              "id" : that.chatId()
               }
               $.ajax({
                    type: "POST",
                    contentType : 'application/json; charset=utf-8',
                    dataType : 'json',
                    url: "chatWithUser/checkChatId",
                    data: JSON.stringify(chatId),
                    success :function(result) {
                    that.chatId(result.id);
                  }});

            //document.getElementById('currentChatId').innerHTML = "Chat id : " + that.chatId();
            document.getElementById('chatIdToCopy').innerHTML = that.chatId();



             var miniChat = {
              "id" : that.chatId(),
              "user" : that.userName(),
              "message" : that.message()
            }
			var form = $("#joinChatForm");
			that.activePollingXhr(
			$.ajax({
			url : "chatWithUser/getMessages",
			type : "POST",
            contentType : 'application/json; charset=utf-8',
            dataType : 'json',
            data: JSON.stringify(miniChat),
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

			             var miniChat = {
                          "id" : that.chatId(),
                          "user" : that.userName(),
                          "message" : $("#postMessageForm input[name=message]").val()
                        }

				$.ajax({
			        url : "chatWithUser/postMessage",
			        type : "POST",
                    contentType : 'application/json; charset=utf-8',
                    dataType : 'json',
                    data: JSON.stringify(miniChat),
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
			that.messages([]);
			that.chatId('');
			that.chatContent('');
			location.reload();
		}
	}

	ko.applyBindings(new ChatViewModel());
});
