package AbaloneGame;

import java.io.IOException;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import org.json.JSONException;
import org.json.JSONObject;



@ServerEndpoint("/serverEndPoint")
public class serverEndPoint
{
	Session Mainsession;
	GameManager gm;
	int BoardLayout = 0;
	
	
	@OnOpen
	/**
	 * The program handles the start of the connection between server and client.
	 * The program gets session ses which is the mainsession of the server.
	 * @pram ses – the session of client
	 */
	public void handleOpen(Session ses)
	{
		gm = new GameManager(this);
		System.out.println("client is now connecting...");
		Mainsession = ses;
	}
	
	@OnMessage
	/**
	 * The program handles all the messages which the server receives.
	 * The program gets message which can be a simple string or jasonObject.
	 * @param message - the message from client
	 */
	public void handleMessage(String message)
	{
		
		
		
		switch(message)
		{
			case "Start Player VS Player":
			{
				System.out.println("recived message to start game Player vs Player ");
				gm = new GameManager(this);
				gm.StartGamePlayerVsPlayer(BoardLayout);
				break;
			}
			case "Start Player VS AI":
			{
				System.out.println("recived message to start game AI vs Player ");
				gm = new GameManager(this);
				gm.StartGamePlayerVsAI(BoardLayout);
				break;
			}
			case "Classic Board Layout":
			{
				BoardLayout = 0;
				break;
			}
			case "Pro Board Layout": 
			{
				BoardLayout = 1;
				break;
			}
			case "Snake Board Layout": 
			{
				BoardLayout = 2;
				break;
			}
			case "Wall Board Layout": 
			{
				BoardLayout = 3;
				break;
			}
			default:
			{
				System.out.println("recived from client " + message);
				
				JSONObject obj = new JSONObject();
				try {
					obj = new JSONObject(message);
				} catch (JSONException e) {
					System.out.println("error json 1234");
					e.printStackTrace();
				}
				try {
					//int posx = Integer.parseInt((String) obj.get("positoinX"));
					//int posy = Integer.parseInt((String) obj.get("positoinY"));
					int posindex = Integer.parseInt((String) obj.get("posIndex"));
					gm.rereceivedMessage(posindex);			
	
	
				} catch (NumberFormatException | JSONException e) {
					System.out.println("error json 122334");
					e.printStackTrace();
				}
				break;
			}	
		}
	}
	
	
	
	
	
	
	
		/**
		 * the program updates at the client one position.
		 * send to client the position and the new value to put inside.
		 * @param indexx - the index of position.
		 * @param val - the value of position.
		 */
		public void sendmessage(int indexx, int val)
		{
			
			JSONObject obje = new JSONObject();
		    try {
				obje.put("index", indexx);
				obje.put("value", val);

			} catch (JSONException e) {
				e.printStackTrace();
			}
		    
		    String mess = obje.toString();
		    
		    try {
				Mainsession.getBasicRemote().sendText(mess);
				System.out.println("sent to client " + mess);

			} catch (IOException e) {
				e.printStackTrace();
			}	
		}
	
	
	
	
	@OnClose
	/**
	 *  program prints that sever has disconnected
	 */
	public void handleClose()
	{
		System.out.println("client is now disconnecting...");
	}
	
	@OnError
	/**
	 * the program prints that error have occurred 
	 * @param t - the error
	 */
	public void handleError(Throwable t)
	{
		System.out.println("error1111");
		t.printStackTrace();
	}


	/**
	 * the program sends the client the player who won the game.
	 * 1 for blue, -1 for red.
	 * @param value - player number.
	 */
	public void sendWinMessage(int value)
	{
		String a = Integer.toString(value);
		try {
			Mainsession.getBasicRemote().sendText("win found "+ a );
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
















