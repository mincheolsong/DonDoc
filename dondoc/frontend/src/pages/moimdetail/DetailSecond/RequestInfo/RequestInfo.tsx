import { useEffect, useState } from "react";
import styles from "./RequestInfo.module.css";
import axios from "axios";
import { BASE_URL } from "../../../../constants";
import MissionIcon from "/src/assets/MoimLogo/missionicon.svg"
// import MoneyIcon from "/src/assets/MoimLogo/moneyicon.svg"

type Props = {
  setInfoModalOpen(id: boolean): void,
  moimId: string | undefined,
  requestType: number,
  requestId: number,
  token: string | undefined
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
  withdrawId: number
}
type missionInfo = {
  amount: number,
  content: string,
  endDate: string,
  imageNumber: number,
  missionId: number,
  missionMemberName: string
  status: number,
  title: string
}
const DefaultRequest = {
  amount: 0,
  category: {id:0, name:""},
  content: "",
  imageNumber: 0,
  moimMemberName: "",
  status: 0,
  title: "",
  withdrawId: 0
}
const DefaultMission = {
  amount: 0,
  content: "",
  endDate: "",
  imageNumber: 0,
  missionId: 0,
  missionMemberName: "",
  status: 0,
  title: ""
}


function RequestInfo({setInfoModalOpen, moimId, requestType, token, requestId}: Props) {

  const [requestInfo, setRequestInfo] = useState<requestInfo>(DefaultRequest)
  const [missionInfo, setMissionInfo] = useState<missionInfo>(DefaultMission)

  const CloseInfoModal = () => {
    setInfoModalOpen(false)
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
          console.log('미션요청:', res.data.response.mission)
          setMissionInfo(res.data.response.mission)
        } else{
          console.log('출금요청:', res.data.response.withdrawRequest)
          setRequestInfo(res.data.response.withdrawRequest)
        }
      }
      catch(err) {
        console.log(err)
      }
    }
    fetchData();
  }, []);


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
              <br />
              <div className={styles.requestdetail}>
                <h2 className={styles.requestlabel}>미션상세</h2>
                <div className={styles.requestcontentbox}>
                  <p>{missionInfo.content}원</p>
                </div>
              </div>
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
                <h2 className={styles.requestlabel}>미션상세</h2>
                <div className={styles.requestcontentbox}>
                  <p>{requestInfo.content}원</p>
                </div>
              </div>
            </div>
          )}
        </div>

        {requestType ? (
          <div className={styles.btns}>
            {/* <button onClick={CloseInfoModal}>닫아라</button> */}
            <button onClick={CloseInfoModal} className={styles.refusebtn}>거절하기</button>
            <button onClick={CloseInfoModal} className={styles.acceptbtn}>승인하기</button>
          </div>
        ):(
          <div className={styles.btns}>
            {/* <button onClick={CloseInfoModal}>닫아라</button> */}
            <button onClick={CloseInfoModal} className={styles.refusebtn}>거절하기</button>
            <button onClick={CloseInfoModal} className={styles.acceptbtn}>승인하기</button>
          </div>
        )}
      </div>
    </div>
  );
}

export default RequestInfo;
