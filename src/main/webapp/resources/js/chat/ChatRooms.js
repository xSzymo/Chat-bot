function copyToClipboard(text) {
  window.prompt("Copy to clipboard: Ctrl+C, Enter", text);
}

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

           function sleep(miliseconds) {
                var currentTime = new Date().getTime();
                while (currentTime + miliseconds >= new Date().getTime()) {
                }
            }



		function pollForMessages() {
			if (!keepPolling) {
				return;
			}

		if(that.chatId() == null) {
               $.ajax({
                 type: "GET",
                 url: "chatRoom/createChatId",
                 success: function(result) {
                 that.chatId(result);
                }});
           	}

             var miniChat = {
              "id" : that.chatId()
               }
               $.ajax({
                    type: "POST",
                    contentType : 'application/json; charset=utf-8',
                    dataType : 'json',
                    url: "chatRoom/checkChatId",
                    data: JSON.stringify(miniChat),
                    success :function(result) {
                    that.chatId(result);
                  }});


            //document.getElementById('currentChatId').innerHTML = "Chat id : " + that.chatId();
            //
            //
           // document.getElementById('chatIdToCopy').innerHTML = that.chatId();




			var form = $("#joinChatForm");
			that.activePollingXhr($.ajax({url : form.attr("action"), type : "GET",
			 data : form.serialize(),cache: false,
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

//             var miniChat = {
//                "id" : that.chatId123(),
//                "message" : that.message(),
//                "messages" : that.messages(),
//                "user" : that.userName()
//               }
//			that.activePollingXhr(
//			$.ajax({
//                    type: "POST",
//                    contentType : 'application/json; charset=utf-8',
//                    dataType : 'json',
//                    url: "chatRoom/getMessages",
//                    data: JSON.stringify(miniChat),
//			        //cache: false,
//				success : function(messages) {
//                    var text = "";
//
//                    for(var i = 0; i < messages.length; i++)
//                        text += messages[i] + "\n";
//
//                    that.chatContent(text);
//				},
//				error : function(xhr) {
//					if (xhr.statusText != "abort" && xhr.status != 503) {
//						resetUI();
//						console.error("Unable to retrieve chat messages. Chat ended.");
//					}
//				sleep(10000);
//				},
//				complete : pollForMessages
//			}));
//			$('#message').focus();
//		}


		that.postMessage = function() {
			if (that.message().trim() != '') {
				var form = $("#postMessageForm");
				$.ajax({url : form.attr("action"), type : "POST",
					  data : "chatId=" + that.chatId() + "&message=" + $("#postMessageForm input[name=message]").val() + "&user=" + that.userName(),
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
