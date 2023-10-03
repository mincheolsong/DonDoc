import { useState, useEffect } from "react";
import styles from "./Friend_re.module.css";
import axios from "axios";
import { BASE_URL } from "../../../../constants";
import { UserType } from "../../../../store/slice/userSlice";
import { useSelector } from "react-redux";
import BackLogoHeader from "../../../toolBox/BackLogoHeader/BackLogoHeader";
import { useLocation, useNavigate } from "react-router-dom";



function Friend_Re() {


  const location = useLocation()
  const Requests = location.state.Requests
  
  useEffect(() => {
    console.log(Requests)

  })

  const userInfo:UserType = useSelector((state:{user:UserType})=>{
    return state.user
  })
  const token = userInfo.accessToken



  return (
    <>
    <div className={styles.Background}>
      <div>
      <BackLogoHeader name="알림" left="6rem" top="0.9rem" fontSize="1.8rem" />
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
        >무시하기</button>
        <button className={styles.myProfileBtn} style={{height:"5rem",fontSize:"1.2rem"}} 
        >프로필 가기</button>
      </div>
    </div>
      )) : <div>친구요청이 없습니다.</div>}
      <div className={styles.topContainer}>
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
      >수락하기</button>
    </div>
  </div>
    </div>
    </>
  );
}

export default Friend_Re;
