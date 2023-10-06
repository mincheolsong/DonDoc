import { useState, useEffect } from "react";
import styles from "./noti_list.module.css";
import axios from "axios";
import { BASE_URL } from "../../constants";
import { UserType } from "../../store/slice/userSlice";
import { useSelector } from "react-redux";
import BackLogoHeader from "../toolBox/BackLogoHeader/BackLogoHeader";
import { useNavigate } from "react-router-dom";

type Request = {
  frinedId:number,
  id:number,
  imageNumber:number,
  nickName:string,
  status: number,
  createdAt: string
}

function Noti_List() {
  const [FriendRe, setFriendRe] = useState<Request[]>([])
  const [Cnt, setCnt] = useState<number>(0)

  const navigate = useNavigate()

  const goDetail = () => {
    navigate('FriendRequests')
  }

  const userInfo:UserType = useSelector((state:{user:UserType})=>{
    return state.user
  })
  const token = userInfo.accessToken

  useEffect(() => {
    FriendRequest()
  },[])

  const FriendRequest = async() => {
    try {
      const response = await axios.get(`${BASE_URL}/api/friend/request/receive/list`,{
        headers: {
        'Content-Type': 'application/json', 
        'Authorization': 'Bearer ' + token
      }}
      );
      setFriendRe(response.data.response.list)
      setCnt(response.data.response.list.length)
      // console.log(response.data.response)
    } catch(error) {
      // console.log('error:', error)
    }
  }

  return (
    <>
    <div className={styles.Background}>
      <div>
      <BackLogoHeader name="알림" left="6rem" top="2%" fontSize="1.8rem" />
      </div>
    <div className={styles.topContainer} onClick={goDetail}>
      <div style={{display:"flex",width:"80%"}}>
      {Cnt ?
      <img src={`/src/assets/characterImg/${FriendRe[Cnt-1].imageNumber}.png`} style={{width:"35%"}} />:
      <img src={`/src/assets/characterImg/0.png`} style={{width:"35%"}} />}
      {Cnt ?
      <div className={styles.CntBox}>{Cnt}</div> :
      <></>
      }
    <div style={{marginLeft:"1rem",textAlign:"center",display:"flex",justifyContent:"center",flexDirection:"column",alignItems:"center",width:"100%"}}>
      <p style={{fontSize:"1.6rem",fontWeight:"bold",fontFamily:"BD"}}>친구추가 요청</p>
      <p style={{fontSize:"1rem",fontFamily:"LH",fontWeight:"bold"}}>친구추가를 하거나 무시합니다.</p>
    </div>
      </div>
  </div>
    </div>
    </>
  );
}

export default Noti_List;
