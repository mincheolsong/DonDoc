import styles from "./MoimHome.module.css";
// import haaland from '../../../assets/bbakbbakyee.jpg'
// import { useEffect } from "react";
import peter from "../../../assets/image/peter.svg"
import Header from "../../webmain/Header/Header";
import { useNavigate } from "react-router-dom";
import { useSelector } from "react-redux";
import { UserType } from "../../../store/slice/userSlice";
import { useEffect, useState } from "react";
import axios from "axios";
import MoimInviteUnit from "./MoimInviteUnit/MoimInviteUnit";
import { BASE_URL } from "../../../constants";

type myMoimList = { moim: object, 
  moimId:number,
  moimName:string,
  introduce:string,
  moimType:number,
  identificationNumber:string}


function MoimHome() {

  const userInfo:UserType = useSelector((state:{user:UserType})=>{
    return state.user
  })
  const token = userInfo.accessToken

  const ShowState = () => {
    console.log(myMoimList)
    console.log(moimInviteList)
  }

  const navigate = useNavigate()

  const GoMoimDetail = () => {
    navigate('/detailfirst')
  }

  const [myMoimList, setMyMoimList] = useState<myMoimList[]>([])
  const [moimInviteList, setMoimInviteList] = useState<[]>([])

  // const [userData, setUserData] = useState<[]>([])
  
  useEffect(() => {
    const fetchData = async () => {
      try {
        const res = await axios.get(`${BASE_URL}/api/moim/list`, {
          headers: {
            'Content-Type': 'application/json', 
            'Authorization': 'Bearer ' + token
          }
        });
        console.log('모임 검색결과:', res.data.response)
        setMyMoimList(res.data.response)
      }
      catch(err) {
        console.log(err)
      }
    }
    const MoimInviteList = async() => {
      try {
        const res = await axios.get(`${BASE_URL}/api/moim/invite/list`, {
          headers: {
            'Content-Type': 'application/json', 
            'Authorization': 'Bearer ' + token
          }
        });
        console.log('나에게 온 초대:', res.data.response)
        setMoimInviteList(res.data.response)
      }catch(err) {
        console.log(err)
      }
    }
    fetchData();
    MoimInviteList();
  }, []);

  return (
    <div className={styles.container}>
      <div className={styles.content}>

        <Header />
        <UserBox userCharacter={peter} username="Jaden" rightBtn="모임 생성"/>

        <div className={styles.moimlist}>
          <div className={styles.moimlisttitle}>
            <h1>나의 모임</h1>
          </div>

          <div className={styles.moimcontent}>
            {myMoimList.length > 0 && myMoimList.map((moim, index) => (
              <h1 className={styles.moimunit} key={index} onClick={GoMoimDetail}>{moim.moimName}</h1>
            ))}
          </div>

        <div className={styles.invitelist}>
          <div className={styles.inviteisttitle}>
            <h1>초대현황</h1>
          </div>

          <div className={styles.invitecontent}>
            <div className={styles.invitebox}>
              <div className={styles.inviteunit}>
                <MoimInviteUnit />
              </div>
            </div>
          </div>

        </div>

      <button onClick={ShowState}>보여주라</button>

      </div>
    </div>
    </div>
  );
}

export default MoimHome;


type props = {
  userCharacter: string,
  username: string,
  rightBtn: string
}

export function UserBox(props:props){
  const navigate = useNavigate()
  const ToCreateMoim = () => {
    navigate('/createmoim')
  }

  return(
    <div className={styles.topContainer}>
      <div style={{display:"flex",width:"60%"}}>
        <img src={props.userCharacter} style={{width:"35%"}} />
        <div style={{marginLeft:"1rem",textAlign:"center"}}>
          <p style={{fontSize:"1.2rem",fontWeight:"bold"}}>{props.username} 의 DonDoc</p>
          <button className={styles.myProfileBtn} onClick={()=>{
            navigate("/mypage")
            }}> 나의프로필가기</button>
        </div>
      </div>
      
      <div>
        <button className={styles.myProfileBtn} style={{height:"5rem",fontSize:"1.2rem"}} onClick={ToCreateMoim} > {props.rightBtn}</button>
      </div>
    </div>

  )

}
