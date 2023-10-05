import { useState, useEffect } from "react";
import styles from "./Friend_re.module.css";
import axios from "axios";
import { BASE_URL } from "../../../../constants";
import { UserType } from "../../../../store/slice/userSlice";
import { useSelector } from "react-redux";
import BackLogoHeader from "../../../toolBox/BackLogoHeader/BackLogoHeader";
import { useLocation } from "react-router-dom";

type Request = {
  id: number,
  friendId: number,
  status: number,
  createdAt: string
}

function Friend_Re() {
    const [Requests, setRequests] = useState<Request[]>([])

    const location = useLocation()
    const state = location.state

    const userInfo:UserType = useSelector((state:{user:UserType})=>{
      return state.user
    })
    const token = userInfo.accessToken
  
  useEffect(() => {
    setRequests(state.Requests)
  },[])

  const Reject = (id:number) => {
    axios.put(`${BASE_URL}/api/friend/request/deny/${id}`, {
      headers:{
        'Content-Type': 'application/json', 
        'Authorization': `Bearer ${token}`
      }
    })
    .then((res) => {
      console.log(res.data)
    })
    .catch((err) => {
      console.log(err)
    })
    
  }



  return (
    <>
    <div className={styles.Background}>
      <div>
      <BackLogoHeader name="친구 요청" left="6rem" top="0.9rem" fontSize="1.8rem" />
      </div>
      {Requests.length ? Requests.map((Re) => (
        <div className={styles.topContainer}>
        <div style={{display:"flex",width:"60%"}}>
        <img src='' style={{width:"35%"}} />
      <div style={{marginLeft:"1rem",textAlign:"center"}}>
        <p style={{fontSize:"1.2rem",fontWeight:"bold"}}>친구이름</p>
      </div>
        </div>
      <div style={{display:"flex", width:"50%"}}>
        <button className={styles.IgnoreBtn} style={{height:"5rem",fontSize:"1.2rem"}}
        onClick={() => Reject(Re.id)} 
        >무시하기</button>
        <button className={styles.myProfileBtn} style={{height:"5rem",fontSize:"1.2rem"}} 
        >프로필 가기</button>
      </div>
    </div>
      )) : <div>친구요청이 없습니다.</div>}
      {/* <div className={styles.topContainer}>
      <div style={{display:"flex",width:"60%"}}>
      <img src='' style={{width:"35%"}} />
    <div style={{marginLeft:"1rem",textAlign:"center"}}>
      <p style={{fontSize:"1.2rem",fontWeight:"bold"}}>친구이름</p>
    </div>
      </div>
    <div style={{display:"flex", width:"50%"}}>
      <button className={styles.IgnoreBtn} style={{height:"5rem",fontSize:"1.2rem"}} 
      >무시하기</button>
      <button className={styles.myProfileBtn} style={{height:"5rem",fontSize:"1.2rem"}} 
      >프로필 가기</button>
    </div>
  </div> */}
    </div>
    </>
  );
}

export default Friend_Re;
