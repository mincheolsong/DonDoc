import { useState, useEffect } from "react";
import styles from "./noti_list.module.css";
import axios from "axios";
import { BASE_URL } from "../../constants";
import { UserType } from "../../store/slice/userSlice";
import { useSelector } from "react-redux";
import BackLogoHeader from "../toolBox/BackLogoHeader/BackLogoHeader";
import { useNavigate } from "react-router-dom";



function Noti_List() {
  const [FriendRe, setFriendRe] = useState<[]>([])

  const navigate = useNavigate()

  const goDetail = () => {
    navigate('FriendRequests', {
      state: {
        Requests: FriendRe
      }
    })
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
      setFriendRe(response.data.response)
      console.log(response.data.response)
    } catch(error) {
      console.log('error:', error)
    }
  }

  return (
    <>
    <div className={styles.Background}>
      <div>
      <BackLogoHeader name="알림" left="6rem" top="0.9rem" fontSize="1.8rem" />
      </div>
    <div className={styles.topContainer} onClick={goDetail}>
      <div style={{display:"flex",width:"60%"}}>
      <img src={userInfo.imageNumber} style={{width:"35%"}} />
    <div style={{marginLeft:"1rem",textAlign:"center"}}>
      <p style={{fontSize:"1.6rem",fontWeight:"bold"}}>친구추가 요청</p>
      <p style={{fontSize:"1rem"}}>친구추가를 하거나 무시합니다.</p>
    </div>
      </div>
  </div>
    </div>
    </>
  );
}

export default Noti_List;
