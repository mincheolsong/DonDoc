import React, { useState } from "react";
import styles from "./RequestModal.module.css";
import MissionIcon from "/src/assets/MoimLogo/missionicon.svg"
import MoneyIcon from "/src/assets/MoimLogo/moneyicon.svg"
import axios from "axios";
import { useSelector } from "react-redux";
import { UserType } from "../../../../store/slice/userSlice";
import { BASE_URL } from "../../../../constants";

type Props = {
  setModalOpen(id: boolean) : void;
  userId: number;
  moimId: string | undefined;
  userType: number;
}

function RequestModal({setModalOpen, userId, moimId, userType}: Props) {

  const userInfo:UserType = useSelector((state:{user:UserType})=>{
    return state.user
  })
  const token = userInfo.accessToken

  const moimIdNumber = moimId ? parseInt(moimId, 10) : undefined;

  const [nowSelected, setNowSelected] = useState<boolean>(true)
  
  const [moneyTitle, setMoneyTitle] = useState<string>('')
  const [moneyAmount, setMoneyAmount] = useState<number>(0)
  const [moneyContent, setMoneyContent] = useState<string>('')
  const [moneyCategory, setMoneyCategory] = useState<number>(0)
  
  const [missionTitle, setMissionTitle] = useState<string>('')
  const [missionAmount, setMissionAmount] = useState<number>(0)
  const [missionContent, setMissionContent] = useState<string>('')
  const [deadLine, setDeadLine] = useState<string>("2023-10-03")



  const ClickMissionTab = () => {
    setNowSelected(false)
    // console.log(nowSelected)
  }
  
  const ClickMoneyTab = () => {
    setNowSelected(true)
    // console.log(nowSelected)
  }

  const ModalClose = () => {
    setModalOpen(false)
  }

  const ChangeMoneyTitle = (e: React.ChangeEvent<HTMLInputElement>) => {
    setMoneyTitle(e.target.value)
    // console.log(e.target.value)
  }
  const ChangeMoneyAmount = (e: React.ChangeEvent<HTMLInputElement>) => {
    const newValue = e.target.value !== '' ? parseInt(e.target.value, 10) : 0;
    setMoneyAmount(newValue);
    // console.log(newValue);
  }
  const ChangeMoneyContent = (e: React.ChangeEvent<HTMLTextAreaElement>) => {
    setMoneyContent(e.target.value)
    // console.log(e.target.value)
  }
  const ChangeMoneyCategory = (e: React.ChangeEvent<HTMLSelectElement>) => {
    const selectedValue = parseInt(e.target.value, 10);
    setMoneyCategory(selectedValue);
    // console.log(selectedValue);
}


  const ChangeMissionTitle = (e: React.ChangeEvent<HTMLInputElement>) => {
    setMissionTitle(e.target.value)
    // console.log(e.target.value)
  }
  const ChangeMissionAmount = (e: React.ChangeEvent<HTMLInputElement>) => {
    const newValue = e.target.value !== '' ? parseInt(e.target.value, 10) : 0;
    setMissionAmount(newValue);
    // console.log(newValue);
  }
  const ChangeMissionContent = (e: React.ChangeEvent<HTMLTextAreaElement>) => {
    setMissionContent(e.target.value)
    // console.log(e.target.value)
  }
  const ChangeMissionDeadLine = (e: React.ChangeEvent<HTMLInputElement>) => {
    setDeadLine(e.target.value)
    // console.log(e.target.value)
  }

  const RequestMoney = async() => {
    const data = {
      "amount": moneyAmount,
      "categoryId": moneyCategory,
      "content": moneyContent,
      "moimId": moimIdNumber,
      "title": moneyTitle
    }
    try {
      const response = await axios.post(`${BASE_URL}/api/moim/withdraw_req`, data, {
        headers: {
          'Content-Type': 'application/json', 
          'Authorization': 'Bearer ' + token
        }
      });
      if(response.data.success) {
        alert('요청이 완료되었습니다.')
        window.location.reload()
      } else {
        alert(response.data.error.message)
      }
    } catch(error) {
      console.log('error:', error)
    }
  }

  const data ={
    "amount": missionAmount,
    "content": missionContent,
    "endDate": deadLine,
    "missionMemberId": 0,
    "moimId": moimIdNumber,
    "title": missionTitle
  }
  if (userType) {
    data.missionMemberId = userId;
  }

  const RequestMission = async() => {
    try {
      const response = await axios.post(`${BASE_URL}/api/moim/mission_req`, data, {
        headers: {
          'Content-Type': 'application/json',
          'Authorization': 'Bearer ' + token
        }
      });
      if (response.data.success) {
        alert('요청이 완료되었습니다.')
        window.location.reload()
      } else {
        alert(response.data.error.message)
      }
    } catch(error) {
      console.log('error:', error)
    }
  }

  return (
    <div className={styles.container}>
      <div className={styles.content}>

        <div className={styles.toptab}>
          <div className={styles.requestmoney} onClick={ClickMoneyTab}>
            <div className={styles.icon}>
              <img src={MoneyIcon} alt="" />
            </div>
            <div className={styles.requesttext}>
              <p style={{color: nowSelected ? '#7677E8' : '', borderBottom: nowSelected ? '2px solid #7677E8' : ''}}>요청하기</p>
            </div>
          </div>

          <div className={styles.requestmission} onClick={ClickMissionTab}>
            <div className={styles.icon}>
              <img src={MissionIcon} alt="" />
            </div>
            <div className={styles.requesttext}>
              <p style={{color: !nowSelected ? '#DD7979' : '', borderBottom: !nowSelected ? '2px solid #DD7979' : ''}}>미션등록</p>
            </div>
          </div>
        </div>


        <div className={styles.selectcontent}>
          {nowSelected ? (
            <>
              <div className={styles.inputs}>
                <div className={styles.requestname}>
                  <input type="text" placeholder="요청명" onChange={ChangeMoneyTitle} value={moneyTitle}/>
                </div>
                <div className={styles.requestcost}>
                  <input type="text" placeholder="금액(원)" onChange={ChangeMoneyAmount} value={moneyAmount}/>
                </div>
                <div className={styles.requestinfo}>
                <textarea placeholder="요청상세" onChange={ChangeMoneyContent} value={moneyContent}></textarea>
                </div>
                <div className={styles.requestcategory}>
                  <select name="category" id="category" onChange={ChangeMoneyCategory} value={moneyCategory}>
                    <option value={0}>쇼핑</option>
                    <option value={1}>교육</option>
                    <option value={2}>식사</option>
                    <option value={3}>여가</option>
                    <option value={4}>생활요금</option>
                    <option value={5}>의료</option>
                    <option value={6}>기타</option>
                  </select>
                </div>

              </div>

              <div className={styles.btns}>
                <button onClick={ModalClose}>닫기</button>
                <button onClick={RequestMoney}>등록하기</button>
              </div>

            </>
          ) : (
            <>
              <div className={styles.inputs}>

                <div className={styles.requestname}>
                  <input type="text" placeholder="미션명" onChange={ChangeMissionTitle} value={missionTitle}/>
                </div>
                <div className={styles.requestname}>
                  <input type="text" placeholder="금액(원)" onChange={ChangeMissionAmount} value={missionAmount}/>
                </div>
                <div className={styles.requestname}>
                  <textarea placeholder="요청상세" onChange={ChangeMissionContent} value={missionContent}></textarea>
                </div>
                <div className={styles.requestname}>
                  <label htmlFor="">종료일자</label>
                  <input type="date" onChange={ChangeMissionDeadLine} value={deadLine}/>
                </div>

              </div>


              <div className={styles.btns}>
                <button onClick={ModalClose}>닫기</button>
                <button onClick={RequestMission}>등록하기</button>
              </div>

            </>
          )}
        </div>



      </div>
    </div>
  );
}

export default RequestModal;
