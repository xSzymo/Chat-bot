function copyToClipboard(text) {
  window.prompt("Copy to clipboard: Ctrl+C, Enter", text);
}

$(document).ready(function() {

	function ChatViewModel() {

		var that = this;
		var myId = "";

		that.userName = ko.observable('');
		that.chatContent = ko.observable('');
		that.message = ko.observable('');
		that.messages = ko.observable([]);
		that.chatId = ko.observable(null);
		that.activePollingXhr = ko.observable(null);

		var keepPolling = false;

		function wait(ms){
           var start = new Date().getTime();
           var end = start;
           while(end < start + ms) {
             end = new Date().getTime();
          }
        }

		that.joinChat = function() {
				keepPolling = true;
				myId = document.getElementById('chatId').value;
				pollForMessages();
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
            console.log("start : " + myId);

//		if(that.chatId() == null) {
//               $.ajax({
//                 type: "GET",
//                 url: "userChat/createChatId",
//                 success: function(result) {
//                 that.chatId(result.id);
//                }});
//           	}
            console.log("medium : " + myId);

             var miniChat = {
              "id" : myId,
              "user" : that.userName()
               }
               $.ajax({
                    type: "POST",
                    contentType : 'application/json; charset=utf-8',
                    dataType : 'json',
                    url: "userChat/checkChatId",
                    data: JSON.stringify(miniChat),
                    success :function(result) {
                        that.chatId(result.id);
                        document.getElementById('chatIdToCopy').innerHTML = that.chatId();

                         myId = result.id;
// console.log(result);
                  }});
                        sleep(50);

            console.log("end : " + myId);
            console.log("\n");

            //sleep(5000);

           if(that.chatId() != null) {

             var miniChat = {
              "id" : myId,
              "user" : that.userName(),
              "message" : that.message()
            }
			that.activePollingXhr(
			$.ajax( {
			url : "userChat/getMessages",
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
			//sleep(1000);
		}



		that.postMessage = function() {

			if (that.message().trim() != '') {

			             var miniChat = {
                          "id" : myId,
                          "user" : that.userName(),
                          "message" : $("#postMessageForm input[name=message]").val()
                        }

				$.ajax({
			        url : "userChat/postMessage",
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
			//sleep(5000);
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
