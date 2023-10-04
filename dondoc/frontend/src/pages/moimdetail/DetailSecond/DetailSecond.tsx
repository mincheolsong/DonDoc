import styles from "./DetailSecond.module.css";
// import BackLogoHeader from "../../toolBox/BackLogoHeader/BackLogoHeader";
import {useState, useEffect} from 'react'
import MissionIcon from "/src/assets/MoimLogo/missionicon.svg"
import MoneyIcon from "/src/assets/MoimLogo/moneyicon.svg"
import axios from "axios";
import { BASE_URL } from "../../../constants";
import { UserType } from "../../../store/slice/userSlice";
import { useSelector } from "react-redux";
import RequestInfoModal from "./RequestInfo/RequestInfo";

type Props = {
  moimId: string | undefined
}
type category = {
  id: number,
  name: string
}

type withdrawRequestList = {
  amount:string,
  category: category,
  content: string,
  moimMemberName: string,
  status: number,
  title:string,
  withdrawId: number,
  imageNumber: number
}
type missionList = {
  amount: number,
  content: string,
  endDate: string,
  missionMemberName: string,
  status: number,
  title: string,
  missionId: number,
  imageNumber: number
}


function DetailSecond({moimId}: Props) {

  const userInfo:UserType = useSelector((state:{user:UserType})=>{
    return state.user
  })
  const token = userInfo.accessToken

  const [nowSelected, setNowSelected] = useState<boolean>(true)
  const [withdrawRequestList, setWithdrawRequestList] = useState<withdrawRequestList[]>([])
  const [missionList, setMissionList] = useState<missionList[]>([])
  const [infoModalOpen, setInfoModalOpen] = useState<boolean>(false)
  const [requestType, setRequestType] = useState<number>(0)
  const [requestId, setRequestId] = useState<number>(0)

  const OpenInfoModal = (type: number, id:number) => {
    setInfoModalOpen(true)
    setRequestType(type)
    setRequestId(id)
  }
  const CloseInfoModal = () => {
    setInfoModalOpen(false)
  }
  const ClickMissionTab = () => {
    setNowSelected(false)
  }
  const ClickMoneyTab = () => {
    setNowSelected(true)
  }

  useEffect(() => {
    const fetchData = async () => {
      try {
        // moim/detail/${moimId}
        const data = {moimId : moimId}
        const res = await axios.post(`${BASE_URL}/api/moim/list_req`, data, {
          headers: {
            'Content-Type': 'application/json', 
            'Authorization': 'Bearer ' + token
          }
        });
        console.log('요청리스트:', res.data.response)
        setWithdrawRequestList(res.data.response.withdrawRequestList)
        setMissionList(res.data.response.missionList)
      }
      catch(err) {
        console.log(err)
      }
    }
    fetchData();
  }, []);

  return (
    <div className={styles.container}>
      {/* <BackLogoHeader name="오늘부터 다이어트" fontSize="2rem" left="5rem" top="0.8rem"/> */}
      <div className={styles.content}>

      <div className={styles.requests}>
        <div className={styles.pagetitle}>
          <h1>요청 관리하기</h1>
        </div>

        <div className={styles.maincontent}>
          <div className={styles.toptab}>
            <div className={styles.requestmoney} onClick={ClickMoneyTab}>
              <div className={styles.icon}>
                <img src={MoneyIcon} alt="" />
              </div>
              <div className={styles.requesttext}>
                <p style={{color: nowSelected ? '#7677E8' : '', borderBottom: nowSelected ? '2px solid #7677E8' : ''}}>이체요청</p>
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


          <div className={styles.requestlist}>
            {nowSelected ? (
              <>
                {withdrawRequestList.length > 0 && withdrawRequestList.map((money, index) => (
                  <div className={styles.requestunit} key={index} onClick={() => OpenInfoModal(0, money.withdrawId)}>
                    <p>{money.amount}</p>
                    <p>{money.withdrawId}</p>
                    <p>{money.imageNumber}</p>
                    <p>{money.category.id}</p>
                    <p>{money.category.name}</p>
                    <p>{money.content}</p>
                    <p>{money.moimMemberName}</p>
                    <p>{money.status}</p>
                    <p>{money.title}</p>
                  </div>
                ))}
              </>
            ) : (
              <>
                {missionList.length > 0 && missionList.map((mission, index) => (
                  <div className={styles.requestunit} key={index} onClick={() => OpenInfoModal(1, mission.missionId)}>
                    <p>{mission.amount}</p>
                    <p>{mission.missionId}</p>
                    <p>{mission.imageNumber}</p>
                    <p>{mission.content}</p>
                    <p>{mission.endDate}</p>
                    <p>{mission.missionMemberName}</p>
                    <p>{mission.status}</p>
                    <p>{mission.title}</p>
                  </div>
                ))}
              </>
            )}
          </div>


        </div>




      </div>
        

      {infoModalOpen && (
        <>
          <div className={styles.backgroundOverlay} onClick={CloseInfoModal}/>
          <RequestInfoModal setInfoModalOpen={setInfoModalOpen} moimId={moimId} requestType={requestType}  token={token} requestId={requestId}/>
        </>
      )}

      </div>
    </div>
  );
}

export default DetailSecond;
