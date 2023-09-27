import styles from "./DetailFirst.module.css";
import { useState, useEffect } from 'react'
import haaland from "../../../assets/characterImg/0.png"
import RequestModal from "./RequestModal/RequestModal";
import InfoUpdateModal from "./InfoupdateModal/InfoupdateModal";
import InviteModal from "./InviteModal/InviteModal";
import axios from "axios";
import { BASE_URL } from "../../../constants";
import { useSelector } from "react-redux";
import { UserType } from "../../../store/slice/userSlice";

type moimMemberList = {
  userId: number,
  moimMemberId: number,
  userType: number,
  nickname: string,
  accountNumber: string,
  bankCode: number
}
const selectedDefault = {
  userId: 0,
  moimMemberId: 0,
  userType: 0,
  nickname: '',
  accountNumber: '',
  bankCode: 0
}
type moimDetailInfo = {
  balance: number,
  moimName: string,
  moimAccountNumber: string
}
const moimDetailDefault = {
  balance: 0,
  moimName: '',
  moimAccountNumber: ''
}

function DetailFirst() {

  const userInfo:UserType = useSelector((state:{user:UserType})=>{
    return state.user
  })
  const token = userInfo.accessToken

  const [modalOpen, setModalOpen] = useState<boolean>(false)
  const [inviteModalOpen, setInviteModalOpen] = useState<boolean>(false)
  const [infoModalOpen, setInfoModalOpen] = useState<boolean>(false)
  const [moimMemberList, setMoimMemberList] = useState<moimMemberList[]>([])
  const [moimDetailInfo, setMoimDetailInfo] = useState<moimDetailInfo>(moimDetailDefault)
  const [selectedMember, setSelectedMember] = useState<moimMemberList>(selectedDefault)

  const OpenModal = () => {
    setModalOpen(true)
  }
  const CloseModal = () => {
    setModalOpen(false)
  }
  const OpenInviteModal = () => {
    setInviteModalOpen(true)
  }
  const CloseInviteModal = () => {
    setInviteModalOpen(false)
  }
  const OpenINfoModal = () => {
    setInfoModalOpen(true)
  }
  const CloseInfoModal = () => {
    setInfoModalOpen(false)
  }

  const SelectMember = (member:moimMemberList) => {
    setSelectedMember(member)
  }

  useEffect(() => {
    const fetchData = async () => {
      try {
        // moim/detail/${moimId}
        const res = await axios.get(`${BASE_URL}/api/moim/detail/1`, {
          headers: {
            'Content-Type': 'application/json', 
            'Authorization': 'Bearer ' + token
          }
        });
        console.log('모임 멤버:', res.data.response)
        const moimDetail = {
          balance : res.data.response.balance,
          moimName : res.data.response.moimName,
          moimAccountNumber : res.data.response.moimAccountNumber,
        }
        setMoimDetailInfo(moimDetail)
        setMoimMemberList(res.data.response.moimMembers)
        setSelectedMember(res.data.response.moimMembers[0])
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
          <div className={styles.backbutton}>
            <button className={styles.toback}>
              back
            </button>
          </div>
          <div className={styles.pagename}>
            <h3>올해는 다이어트 성공</h3>
          </div>
          <div className={styles.bookicon} onClick={OpenINfoModal}>
            <h3>ICON</h3>
          </div>
        </div>

        <div className={styles.userscontent}>
          
          <div className={styles.usersbox}>
            {moimMemberList.length > 0 && moimMemberList.map((member, index) => (
              <div className={styles.boxunit} key={index} onClick={() => SelectMember(member)}>
                <img src={haaland} alt="" />
                <p>{member.userId}</p>
              </div>
            ))}
          </div>

          <div className={styles.selectuser}>
            <div className={styles.invitebtn}>
              <button className={styles.invbtn} onClick={OpenInviteModal}>+ 초대하기</button>
            </div>
            <div className={styles.selectcharacter}>
              <img src={haaland} alt="" />
            </div>

            <div className={styles.selectaccount}>
              <div className={styles.banklogo}>
                <img src={haaland} alt="" />
              </div>
              <div className={styles.accountinfo}>
                <div className={styles.accounttext}>
                  <p>{selectedMember.bankCode}</p>
                  <p>{selectedMember.accountNumber}</p>
                </div>
              </div>
              <div className={styles.accountowner}>
                <p>{selectedMember.nickname}</p>
              </div>
            </div>

            <div className={styles.optionbuttons}>
              <button onClick={OpenModal}>요청하기</button> <button>요청확인</button>
            </div>
          </div>

          {modalOpen && (
            <>
              <div className={styles.backgroundOverlay} onClick={CloseModal}/>
              <RequestModal setModalOpen={setModalOpen} />
            </>
          )}
          {inviteModalOpen && (
            <>
              <div className={styles.backgroundOverlay} onClick={CloseInviteModal}/>
              <InviteModal setModalOpen={setInviteModalOpen} />
            </>
          )}
          {infoModalOpen && (
            <>
              <div className={styles.backgroundOverlay} onClick={CloseInfoModal}/>
              <InfoUpdateModal setModalOpen={setInfoModalOpen} />
            </>
          )}

        </div>

        <div className={styles.moimaccountcontent}>
          <div className={styles.moimaccountbox}>
            <div className={styles.moimaccountinfo}>
              <div className={styles.moimbanklogo}>
                <img src={haaland} alt="" className={styles.moimaccountlogo} />
              </div>
              <div className={styles.moiminfo}>
                <p style={{marginTop: "0.5rem", marginBottom: "0rem"}}>{moimDetailInfo.moimName}</p>
                <p style={{marginTop: "0.5rem", marginBottom: "0rem"}}>{moimDetailInfo.moimAccountNumber}</p>
              </div>
            </div>
            <div className={styles.accountbalance}>
              <div className={styles.accountwon}>
                <p style={{fontSize: "2.6rem", fontWeight:'900', margin: "0rem"}}>{moimDetailInfo.balance}</p>
                <p style={{fontSize: "2.6rem", fontWeight:'900', margin: "0rem"}}>원</p>
              </div>
              <div className={styles.chargebalance}>
                <button className={styles.chargebtn}>충전하기</button>
              </div>
            </div>
          </div>
        </div>

      </div>
    </div>
  );
}

export default DetailFirst;
