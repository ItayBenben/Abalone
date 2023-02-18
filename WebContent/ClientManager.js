


window.onload=function(){
  
 
	var webSocket;
	webSocket= new WebSocket("ws://localhost:8080/checkv1/serverEndPoint");
	var messagesTextArea = document.getElementById("messagesTextArea");
	webSocket.onopen = function(message) {processOpen(message);};
	webSocket.onmessage = function(message) {processMessage(message);};
	webSocket.onclose = function(message) {processClose(message);};
	webSocket.onerror = function(message) {processError(message);};
	
	
	function processOpen(message)
	{
	}
	
	function processClose(message)
	{
		webSocket.send("client disconnected...");
		messagesTextArea.value+= "server disconnect...";
	}
	function processError(message)
	{
		messagesTextArea.value += "error....\n";
	}
	
	/**
	 * program receives a message from server.
	 * the message can update one value in position or blue win found or red won found. 
	 * if it is an position update than message will be JSONObject. if not than simple string.
	 */
	function processMessage(message)
	{
		 var data = message.data ; // holds the data received
	        try
	        {
	            var obj = JSON.parse(data);
	        }
	        catch(e)
	        {
	            if(data.localeCompare("win found -1"))
	        	{
	            	alert("Congratulations, red player won!!!");
	        	}
	            else
	            	{
	            	if(data.localeCompare("win found 1"))
		        	{
		            	alert("Congratulations, blue player won!!!");
		        	}
	            	else
	            		return false;
	            	}
	        }
	        
	        
	        //1: blue
	        //2 : pressed blue
	        //-1 : red
	        //-2: pressed red
	        //0: empty 
	        //3: score blue
	        //-3: score red
	        
	        
	        //base blue
	        if(obj.value == 1)
	        	$("div.cell[data-index=" + obj.index+ "]").css('background-color', 'BLUE');
	       //pressed blue
	        if(obj.value == 2)
	        	$("div.cell[data-index=" + obj.index+ "]").css('background-color', '#3399FF');
	        // base red
	        if(obj.value == -1)
	        	$("div.cell[data-index=" + obj.index+ "]").css('background-color', 'red');
	        // pressed red
	        if(obj.value == -2)
	        	$("div.cell[data-index=" + obj.index+ "]").css('background-color', '#FF9933');
	        //empty
	        if(obj.value == 0)
	        	$("div.cell[data-index=" + obj.index+ "]").css('background-color', '#e0e0e0');
	        //score blue
	        if(obj.value == 3)
	        	$("div.cell[data-index=" + obj.index+ "]").css('background-color', 'BLUE');
	        //score red
	        if(obj.value == -3)
	        	$("div.cell[data-index=" + obj.index+ "]").css('background-color', 'red');
	        // empty score
	        if(obj.value == 4)
	        	$("div.cell[data-index=" + obj.index+ "]").css('background-color', '#5F9EA0');		
	        
		
		
		
	
		
	}
	
	
	
	// sends to server given message
	function sendMessage(message)
	{
		
			webSocket.send(message);
		
	}
	
	
	
	
	//on click functions
	$(document).ready(function(){
		
		//on click to start game player vs player
		$('.btnPVP').click(function(){
			var selectedBoardLayout =$('input[name=Layout]:checked').attr('id');
			firstMessage = selectedBoardLayout.concat(" Board Layout");
			sendMessage(firstMessage)
		    var msg = "Start Player VS Player";
	        sendMessage(msg);
		});
		//on click to start game player vs player
		$('.btnPVAI').click(function(){
			var selectedBoardLayout =$('input[name=Layout]:checked').attr('id');
			firstMessage = selectedBoardLayout.concat(" Board Layout");
			sendMessage(firstMessage)
		    var msg = "Start Player VS AI";
	        sendMessage(msg);
		});
		
		// cell on click 
	    $( ".cell").click(function(evt) {  
	    		    	
	    	var indexx = $(this).attr("data-index");
	    	var obj = {"posIndex":indexx}

	    	sendMessage(JSON.stringify( obj));
	    }); 
	}); 
 };





	





