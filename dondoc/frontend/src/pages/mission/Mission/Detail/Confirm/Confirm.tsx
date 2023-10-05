import styles from "./Confirm.module.css";
import { BASE_URL } from "../../../../../constants";
import axios from "axios";
import { UserType } from "../../../../../store/slice/userSlice";
import { useSelector } from "react-redux";

type Missions = {
  id: number,
  moimName: string,
  title: string,
  content: string,
  amount: number,
  endDate: string,
  moimId:number,
}


type Props = {
  setOpenConfirm(id:boolean) : void,
  Mi : Missions
}


function Confirm({setOpenConfirm, Mi}:Props) {

  const CloseModal = () => {
    setOpenConfirm(false)
  }

  const userInfo:UserType = useSelector((state:{user:UserType})=>{
    return state.user
  })

  const token = userInfo.accessToken

  const data = {
    moimId: Mi.moimId,
    requestId: Mi.id
  }

const GiveUpMission = () => {
  axios.post(`${BASE_URL}/api/moim/quit_mission`,data,{
    headers:{
      Authorization: `Bearer ${token}`
    }
  })
  .then((res) => {
    console.log(res.data)
    alert(res.data.response.msg)
    location.reload()
  })
  .catch((err) => {
    console.log(err)
  })
}

  return (

    <div className={styles.container}>
      <div className={styles.content}>
        <div className={styles.maincontent}>
          <p style={{textAlign:"center", lineHeight:"15rem", fontSize:"2rem", fontWeight:"bold"}}>정말 포기하시겠습니까?</p>
          
        </div>
          <div className={styles.btns}>
            <button onClick={GiveUpMission} className={styles.refusebtn}>포기하기</button>
            <button onClick={CloseModal} className={styles.acceptbtn}>닫기</button>
          </div>
        
      </div>
    </div>
  )

}

export default Confirm