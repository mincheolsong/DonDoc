import styles from "./Mission_Detail.module.css";
import { useState } from 'react'
import MissionIcon from "/src/assets/MoimLogo/missionicon.svg"
import Confirm from "./Confirm/Confirm";

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
  setOpenModal(id:boolean) : void,
  Mi : Missions
}


function Mission_Detail({setOpenModal, Mi}:Props) {
  const [OpenConfirm, setOpenConfirm] = useState<boolean>(false)

  const CloseModal = () => {
    setOpenModal(false)
  }

  const ConfirmOpen = () => {
    setOpenConfirm(true)
  }



  return (

    <div className={styles.container}>
      <div className={styles.content}>

        <div className={styles.topbar}>
          <div className={styles.topgroup}>
            <div className={styles.imgbox} style={{marginBottom:'0'}}>
              <img src={MissionIcon} alt="" />
            </div>
            <div className={styles.title} style={{marginTop:'0'}}>
              <h1>미션상세</h1>
            </div>
          </div>
        </div>

        <div className={styles.maincontent}>
            <div className={styles.requestinfo}>
              <div className={styles.requesttext}>
                <p className={styles.requestlabel}>미션명</p>
                <p>{Mi.title}</p>
              </div>
              <div className={styles.requesttext}>
                <p className={styles.requestlabel}>종료기간</p>
                <p>{Mi.endDate}</p>
              </div>
              <div className={styles.requesttext}>
                <p className={styles.requestlabel}>금액</p>
                <p>{Mi.amount}원</p>
              </div>
              <br />
              <hr />
              <br />
              <div className={styles.requestdetail}>
                <h2 className={styles.requestlabel}>미션상세</h2>
                <div className={styles.requestcontentbox}>
                  <p>{Mi.content}</p>
                </div>
              </div>
            </div>
          
        </div>

  
          <div className={styles.btns}>
            <button onClick={ConfirmOpen} className={styles.refusebtn}>포기하기</button>
            <button onClick={CloseModal} className={styles.acceptbtn}>닫기</button>
          </div>
        
      </div>
      {OpenConfirm && <Confirm setOpenConfirm = {setOpenConfirm} Mi={Mi}/>}
    </div>

  )

}

export default Mission_Detail