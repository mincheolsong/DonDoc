import React, { useEffect, useState } from "react";
import styles from "./RequestInfo.module.css";
import axios from "axios";
import { BASE_URL } from "../../../../constants";
import MissionIcon from "/src/assets/MoimLogo/missionicon.svg"
import { useNavigate } from "react-router-dom";
// import MoneyIcon from "/src/assets/MoimLogo/moneyicon.svg"

type Props = {
  setInfoModalOpen(id: boolean): void,
  moimId: string | undefined,
  requestType: number,
  requestId: number,
  token: string | undefined,
  requestState: number,
  memberType:number,
  myPhone: string | undefined,
  moimType: number
}

type category = {
  id: number,
  name: string
}

type requestInfo = {
  amount: number,
  category: category,
  content: string,
  imageNumber: number,
  moimMemberName: string,
  status: number,
  title: string,
  withdrawId: number,
  phoneNumber:string
}
type missionInfo = {
  amount: number,
  content: string,
  endDate: string,
  imageNumber: number,
  missionId: number,
  missionMemberName: string
  status: number,
  title: string,
  phoneNumber:string
}
const DefaultRequest = {
  amount: 0,
  category: {id:0, name:""},
  content: "",
  imageNumber: 0,
  moimMemberName: "",
  status: 0,
  title: "",
  withdrawId: 0,
  phoneNumber:""
}
const DefaultMission = {
  amount: 0,
  content: "",
  endDate: "",
  imageNumber: 0,
  missionId: 0,
  missionMemberName: "",
  status: 0,
  title: "",
  phoneNumber:""
}


function RequestInfo({moimId, requestType, token, requestId, requestState, memberType, myPhone, moimType}: Props) {

  const [requestInfo, setRequestInfo] = useState<requestInfo>(DefaultRequest)
  const [missionInfo, setMissionInfo] = useState<missionInfo>(DefaultMission)
  const [userPassword, setUserPassword] = useState<string>('')

  const navigate = useNavigate();

  const ChangePassword = (e: React.ChangeEvent<HTMLInputElement>) => {
    setUserPassword(e.target.value)
  }

  const AcceptMoney = (type:number) => {
    navigate('/acceptpassword', {state: {moimId:moimId, requestId:requestId, type:type}})
  }

  useEffect(() => {
    const fetchData = async () => {
      try {
        // moim/detail/${moimId}
        const data = {
          moimId: moimId,
          requestType: requestType,
          requestId: requestId
        }
        const res = await axios.post(`${BASE_URL}/api/moim/detail_req`, data, {
          headers: {
            'Content-Type': 'application/json', 
            'Authorization': 'Bearer ' + token
          }
        });
        if(requestType) {
          // console.log('미션요청:', res.data.response.mission)
          setMissionInfo(res.data.response.mission)
        } else{
          // console.log('출금요청:', res.data.response.withdrawRequest)
          setRequestInfo(res.data.response.withdrawRequest)
        }
      }
      catch(err) {
        console.log(err)
      }
    }
    fetchData();
  }, []);

  const RefuseMission = async () => {
    const data = {
      "moimId" : moimId,
      "requestId" : requestId
    }
    try {
      const response = await axios.post(`${BASE_URL}/api/moim/reject_mission`, data, {
        headers: {
          'Content-Type': 'application/json', 
          'Authorization': 'Bearer ' + token
        }
      });
      if (response.data.success == true) {
        // console.log(response.data)
        alert('미션을 거절하였습니다.')
        window.location.reload()
      }
    } catch(error) {
      console.log('error:', error)
    }
  }
  const RefuseMoney = async () => {
    const data = {
      "moimId" : moimId,
      "requestId" : requestId
    }
    try {
      const response = await axios.post(`${BASE_URL}/api/moim/reject_req`, data, {
        headers: {
          'Content-Type': 'application/json', 
          'Authorization': 'Bearer ' + token
        }
      });
      if (response.data.success == true) {
        // console.log(response.data)
        alert('요청을 거절하였습니다.')
        window.location.reload()
      }
    } catch(error) {
      console.log('error:', error)
    }
  }
  const AcceptMission = async () => {
    // console.log('실행!')
    const data = {
      "moimId" : moimId,
      "password" : userPassword,
      "requestId" : requestId
    }
    try {
      const response = await axios.post(`${BASE_URL}/api/moim/allow_mission`, data, {
        headers: {
          'Content-Type': 'application/json', 
          'Authorization': 'Bearer ' + token
        }
      });
      if (response.data.success == true) {
        // console.log(response.data)
        alert('요청을 승인하였습니다.')
        window.location.reload()
      }
    } catch(error) {
      console.log('error:', error)
    }
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
              <h1>요청내역</h1>
            </div>
          </div>
        </div>

        <div className={styles.maincontent}>
          {requestType ? (
            <div className={styles.requestinfo}>
              <div className={styles.requesttext}>
                <p className={styles.requestlabel}>신청인</p>
                <p>{missionInfo.missionMemberName}</p>
              </div>
              <div className={styles.requesttext}>
                <p className={styles.requestlabel}>미션명</p>
                <p>{missionInfo.title}</p>
              </div>
              <div className={styles.requesttext}>
                <p className={styles.requestlabel}>종료일자</p>
                <p>{missionInfo.endDate}</p>
              </div>
              <div className={styles.requesttext}>
                <p className={styles.requestlabel}>금액</p>
                <p>{missionInfo.amount}원</p>
              </div>
              <br />
              <hr />
              <div className={styles.requestdetail}>
                <h2 className={styles.requestlabel}>미션상세</h2>
                <div className={styles.requestcontentbox}>
                  <p>{missionInfo.content}원</p>
                </div>
              </div>
              <input type="password" onChange={ChangePassword} placeholder="미션을 승인하는 경우 비밀번호 입력"/>
            </div>
          ) : (
            <div className={styles.requestinfo}>
              <div className={styles.requesttext}>
                <p className={styles.requestlabel}>신청인</p>
                <p>{requestInfo.moimMemberName}</p>
              </div>
              <div className={styles.requesttext}>
                <p className={styles.requestlabel}>카테고리</p>
                <p>{requestInfo.category.name}</p>
              </div>
              <div className={styles.requesttext}>
                <p className={styles.requestlabel}>사용처</p>
                <p>{requestInfo.title}</p>
              </div>
              <div className={styles.requesttext}>
                <p className={styles.requestlabel}>금액</p>
                <p>{requestInfo.amount}원</p>
              </div>
              <br />
              <hr />
              <br />
              <div className={styles.requestdetail}>
                <h2 className={styles.requestlabel}>요청상세</h2>
                <div className={styles.requestcontentbox}>
                  <p>{requestInfo.content}원</p>
                </div>
              </div>
            </div>
          )}
        </div>

        {/* {!requestState || !memberType ? (
          <>
            {requestType? (
              <div className={styles.btns}>
                <button onClick={RefuseMission} className={styles.refusebtn}>거절하기</button>
                <button onClick={AcceptMission} className={styles.acceptbtn}>승인하기</button>
              </div>
            ):(
              <div className={styles.btns}>
                <button onClick={RefuseMoney} className={styles.refusebtn}>거절하기</button>
                <button onClick={AcceptMoney} className={styles.acceptbtn}>승인하기</button>
              </div>
            )}
          </>
        ) : (
          <>
          </>
        )} */}

        {memberType ? (
          <></>
        ):(
          <>
            {requestType ? (
              <>
                {myPhone == missionInfo.phoneNumber && moimType == 2 ? (
                  <></>
                  ):(
                  <>
                    {requestState ? (
                      <div className={styles.btns}>
                        <button onClick={() => AcceptMoney(1)} className={styles.refusebtn}>미션성공</button>
                        <button onClick={() => AcceptMoney(2)} className={styles.acceptbtn}>미션실패</button>
                      </div>
                    ):(
                      <div className={styles.btns}>
                        <button onClick={RefuseMission} className={styles.refusebtn}>미션거절</button>
                        <button onClick={AcceptMission} className={styles.acceptbtn}>미션승인</button>
                      </div>

                    )}
                  </>
                )}
              </>
            ):(
              <>
                {myPhone == requestInfo.phoneNumber && moimType == 2 ? (
                  <></>
                ):(
                  <>
                    <div className={styles.btns}>
                      <button onClick={RefuseMoney} className={styles.refusebtn}>송금거절</button>
                      <button onClick={() => AcceptMoney(0)} className={styles.acceptbtn}>송금승인</button>
                    </div>
                  </>
                )}
              </>
            )}
          </>
        )}

      </div>
    </div>
  );
}

export default RequestInfo;
