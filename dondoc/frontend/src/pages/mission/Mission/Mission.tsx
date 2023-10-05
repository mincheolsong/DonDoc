import styles from "./Mission.module.css";
import { useState, useEffect } from 'react'
import Header from "../../webmain/Header/Header";
import Nav from "../../Nav/Nav";
import { useSelector } from "react-redux/es/hooks/useSelector";
import { UserType } from "../../../store/slice/userSlice";
import axios from "axios";
import { BASE_URL } from "../../../constants";
import Mission_Detail from "./Detail/Mission_Detail";


type Missions = {
  id: number,
  moimName: string,
  title: string,
  content: string,
  amount: number,
  endDate: string,
  moimId:number
}

const Standard = {
  id: 0,
  moimName: '',
  title: '',
  content: '',
  amount: 0,
  endDate: '',
  moimId:0
}



function Mission() {

  const [MissionList, setMissionList] = useState<Missions[]>([])
  const [OpenModal, setOpenModal] = useState<boolean>(false)
  const [SelectedMission, setSelectedMission] = useState<Missions>(Standard)
  const [MissionTF, setMissionTF] = useState<boolean>(false)

  const userInfo:UserType = useSelector((state:{user:UserType})=>{
    return state.user
  })

  const token = userInfo.accessToken

  const ModalOpen = (mi:Missions) => {
    setOpenModal(true)
    setSelectedMission(mi)
  }

  useEffect(() => {
    axios.get(`${BASE_URL}/api/moim/my_mission`, {
      headers:{
        Authorization: `Bearer ${token}`}
    })
    .then((res) => {
      if (res.data.response.length > 0) {
        setMissionTF(true)
        setMissionList(res.data.response)
      }
      else {
        setMissionTF(false)
      }
      console.log(res.data)
    })
    .catch((err) => {
      console.log(err)
    })
  },[OpenModal])

  return (
    <>
    <Header />
    <div>
    <img style={{marginTop:"5%",marginBottom:"2%",marginLeft:"30%",width:"40%"}} src={`/src/assets/characterImg/${userInfo.imageNumber}.png`} alt="" />
    <div style={{marginTop:"5%",marginBottom:"2%",marginLeft:"33%",width:"40%", fontSize:"2.8rem"}}>나의 미션</div>
    </div>
    <div className={styles.List}>

    { MissionTF ?
    (MissionList.map((mi) => (
      <div className={styles.topContainer} onClick={() => ModalOpen(mi)}>
      <div style={{display:"flex",width:"100%"}}>
      <img src={`/src/assets/MoimLogo/dondoclogo.svg`} style={{width:"25%", marginLeft:"1rem"}} />
    <div style={{marginLeft:"5rem",textAlign:"left", width:"30%"}}>
      <p style={{fontSize:"1.5rem",fontWeight:"bold", color:"skyblue", margin:"0"}}>{mi.moimName}</p>
      <p style={{fontSize:"1.2rem"}}>{mi.title}</p>
      <p style={{fontSize:"1.2rem", marginTop:"0"}}>{mi.endDate}까지</p>
    </div>
    <div className={styles.amount}>
      <p style={{fontSize:"1.5rem",fontWeight:"bold", marginBottom:"0"}}>{mi.amount}원</p>
    </div>
      </div> 
      </div>
       
))) :
<div className={styles.ResultContainer}>등록된 미션이 없습니다.</div>}


    </div>
      {OpenModal && <Mission_Detail setOpenModal={setOpenModal} Mi={SelectedMission}/>}
      <Nav />
    </>
  );
}

export default Mission;


