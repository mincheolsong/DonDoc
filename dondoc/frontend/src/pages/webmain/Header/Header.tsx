import styles from "./Header.module.css";
import dondocLogo from "../../../assets/image/dondoc.svg"
import bellicon from "../../../assets/image/bell.svg"
import bellonicon from "../../../assets/image/bellon.svg"
import { NavLink } from "react-router-dom";
import  {useEffect,useState}  from "react";

interface WebSocketData {
  Authorization: string;
  alarm: string;
}
function Header() {

  const accessToken = "eyJhbGciOiJIUzI1NiJ9.eyJuYW1lIjoi6rCV7Iq57ZiEIiwidXNlcm5hbWUiOiIwMTAyNjgwNzQ1MyIsInN1YiI6IjEiLCJpYXQiOjE2OTY0ODY5MTAsImV4cCI6MTY5NjQ4ODcxMH0.ampnU9V25TYGRYsaDvyvyWhnaVbLr2OYA92D5tp6lRo";
  // 1번 사용자
  const [notifications, setNotifications] = useState<string>('');
  const [socket, setSocket] = useState<WebSocket | null>(null);
  const [inputMessage, setInputMessage] = useState<string>('');
  const [onAlarm, setOnAlarm] = useState<number>(0);

  useEffect(() => {
    if (!socket) {
      const ws = new WebSocket('ws://localhost:9191/websocket');

      
      ws.onopen = () => {
        console.log('WebSocket connected');
        setSocket(ws);

        const headers: WebSocketData = {
          Authorization: `Bearer ${accessToken}`,
          alarm: inputMessage,
        };

        ws.send(JSON.stringify(headers));
      };

      ws.onerror = (event) => {
        console.error('WebSocket error:', event);
      };

      ws.onmessage = (event) => {
        setOnAlarm(1);
        setNotifications(event.data);
      };

      return () => {
        if (socket) {
          socket.close();
        }
      };
    }
  }, [socket]);


  const sendInvite = () => {
    if (socket && inputMessage) {
      const data: WebSocketData = {
        Authorization: `Bearer ${accessToken}`,
        alarm: inputMessage,
      };
      socket.send(JSON.stringify(data));
      setInputMessage('');
    }
  };

  return (
    <div className={styles.header}>
      <img src={dondocLogo} />

      <ul>
        {notifications}
        </ul>
        
     <div>
        <input
          type="text"
          placeholder="메시지를 입력하세요"
          value={inputMessage}
          onChange={(e) => setInputMessage(e.target.value)}
        />
        <button onClick={sendInvite}>초대 보내기</button>
      </div>
      
      <NavLink
        to="/notification">
        {
          onAlarm === 0
            ?
          <img src={bellicon} />
            :
            onAlarm === 1
              ?
              <img src={bellonicon}></img>
              :null
        }
      </NavLink>
      
    </div>
  );
}

export default Header;
