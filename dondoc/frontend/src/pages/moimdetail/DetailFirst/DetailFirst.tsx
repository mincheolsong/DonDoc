import styles from "./DetailFirst.module.css";
import { useState, useEffect } from 'react'
import RequestModal from "./RequestModal/RequestModal";
// import InfoUpdateModal from "./InfoupdateModal/InfoupdateModal";
import InviteModal from "./InviteModal/InviteModal";
import ChargeModal from "./ChargeMoimAccount/ChargeMoimAccount"
import axios from "axios";
import { BASE_URL } from "../../../constants";
import { useSelector } from "react-redux";
import { UserType } from "../../../store/slice/userSlice";
import dondocLogo from "../../../assets/MoimLogo/dondoclogo.svg"
import { useNavigate } from "react-router-dom";

type Props = {
  accountId : number,
  userType : number,
  moimId: string | undefined
}

type moimMemberList = {
  userId: number,
  moimMemberId: number,
  userType: number,
  nickname: string,
  accountNumber: string,
  bankCode: number,
  bankName: string,
  userImageNumber: string,
}
const selectedDefault = {
  userId: 0,
  moimMemberId: 0,
  userType: 0,
  nickname: '',
  accountNumber: '',
  bankCode: 0,
  bankName: '',
  userImageNumber: ''
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

function DetailFirst({userType, accountId, moimId}: Props) {

  const navigate = useNavigate()

  const userInfo:UserType = useSelector((state:{user:UserType})=>{
    return state.user
  })
  const token = userInfo.accessToken

  const [modalOpen, setModalOpen] = useState<boolean>(false)
  const [inviteModalOpen, setInviteModalOpen] = useState<boolean>(false)
  const [chargeModalOpen, setChargeModalOpen] = useState<boolean>(false)
  // const [infoModalOpen, setInfoModalOpen] = useState<boolean>(false)
  const [moimMemberList, setMoimMemberList] = useState<moimMemberList[]>([])
  const [moimDetailInfo, setMoimDetailInfo] = useState<moimDetailInfo>(moimDetailDefault)
  const [selectedMember, setSelectedMember] = useState<moimMemberList>(selectedDefault)
  const [moimIdNumber, setMoimIdNumber] = useState<string>('')

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
  const OpenChargeModal = () => {
    setChargeModalOpen(true)
  }
  const CloseChargeModal = () => {
    setChargeModalOpen(false)
  }
  // const OpenINfoModal = () => {
  //   setInfoModalOpen(true)
  // }
  // const CloseInfoModal = () => {
  //   setInfoModalOpen(false)
  // }

  const SelectMember = (member:moimMemberList) => {
    setSelectedMember(member)
  }

  const CopyAccount = (account:string) => {
    console.log(account)
    navigator.clipboard.writeText(account)
  }

  useEffect(() => {
    const fetchData = async () => {
      try {
        // moim/detail/${moimId}
        const res = await axios.get(`${BASE_URL}/api/moim/detail/${moimId}`, {
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
        // console.log(moimDetail)
        setMoimDetailInfo(moimDetail)
        setMoimMemberList(res.data.response.moimMembers)
        setSelectedMember(res.data.response.moimMembers[0])
        setMoimIdNumber(res.data.response.moimId)
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

        <div className={styles.userscontent}>
          
          <div className={styles.usersbox}>
            {moimMemberList.length > 0 && moimMemberList.map((member, index) => (
              selectedMember.userId === member.userId ? (
                <div style={{backgroundColor: '#526BEA', color: 'white'}} className={styles.boxunit} key={index} onClick={() => SelectMember(member)}>
                  {member.userType === 0 && (
                    <img src={`/src/assets/MoimLogo/bookmark.svg`} className={styles.bookmark} alt="" />
                  )}
                  <img src={`/src/assets/characterImg/${member.userImageNumber}.png`} alt="" />
                  <p>{member.nickname}</p>
                </div>
              ) : (
                <div className={styles.boxunit} key={index} onClick={() => SelectMember(member)}>
                  {member.userType === 0 && (
                    <img src={`/src/assets/MoimLogo/bookmark.svg`} className={styles.bookmark} alt="" />
                  )}
                  <img src={`/src/assets/characterImg/${member.userImageNumber}.png`} alt="" />
                  <p>{member.nickname}</p>
                </div>
              )
            ))}
          </div>

          <div className={styles.selectuser}>
            {userType == 0 ? (
              <div className={styles.invitebtn}>
                <button className={styles.invbtn} onClick={OpenInviteModal}>+ 초대하기</button>
              </div>
            ) : (
              <div className={styles.invitebtn}></div>
            )}
            <div className={styles.selectcharacter}>
              <img src={`/src/assets/characterImg/${selectedMember.userImageNumber}.png`} alt="" />
            </div>

            <div className={styles.selectaccount} onClick={() => CopyAccount(selectedMember.accountNumber)}>
              <div className={styles.banklogo}>
                <img src={`/src/assets/Bank_Logo/${selectedMember.bankCode}.svg`} alt="" />
              </div>
              <div className={styles.accountinfo}>
                <div className={styles.accounttext}>
                  <p>{selectedMember.bankName}</p>
                  <p>{selectedMember.accountNumber}</p>
                </div>
              </div>
              <div className={styles.accountowner}>
                <p style={{marginRight: '1rem'}}>{selectedMember.nickname}</p>
              </div>
            </div>
            
            {selectedMember.userType == 0 ? (
              <div className={styles.optionbuttons}>
                <button onClick={OpenModal}>요청하기</button>
              </div>
            ) : (
              <div className={styles.optionbuttons}>
                <button onClick={() => navigate(`/diffprofile/${selectedMember.userId}`, {state: {diffuserId:selectedMember.userId}})}>프로필 가기</button>
                <button onClick={OpenModal}>요청하기</button>
              </div>
            )}
          </div>

          {modalOpen && (
            <>
              <div className={styles.backgroundOverlay} onClick={CloseModal}/>
              <RequestModal setModalOpen={setModalOpen} userId={selectedMember.userId} moimId={moimId} userType={selectedMember.userType}/>
            </>
          )}
          {inviteModalOpen && (
            <>
              <div className={styles.backgroundOverlay} onClick={CloseInviteModal}/>
              <InviteModal setModalOpen={setInviteModalOpen} moimIdNumber={moimIdNumber}/>
            </>
          )}
          {chargeModalOpen && (
            <>
              <div className={styles.backgroundOverlay} onClick={CloseChargeModal}/>
              <ChargeModal setModalOpen={setChargeModalOpen} accountId={accountId} toAccount={moimDetailInfo.moimAccountNumber}/>
            </>
          )}
          {/* {infoModalOpen && (
            <>
              <div className={styles.backgroundOverlay} onClick={CloseInfoModal}/>
              <InfoUpdateModal setModalOpen={setInfoModalOpen} />
            </>
          )} */}

        </div>
        {userType == 0 ? (
          <div className={styles.moimaccountcontent}>
            <div className={styles.moimaccountbox}>
              <div className={styles.moimaccountinfo}>
                <div className={styles.moimbanklogo}>
                  <img src={dondocLogo} alt="" className={styles.moimaccountlogo} />
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
                  <button className={styles.chargebtn} onClick={OpenChargeModal}>충전하기</button>
                </div>
              </div>
            </div>
          </div>
        ) : (
          <div className={styles.moimaccountcontent}>
            <div className={styles.moimaccountbox}>
              <div className={styles.moimaccountinfo}>
                <div className={styles.moimbanklogotwo}>
                  <img src={dondocLogo} alt="" className={styles.moimaccountlogo} />
                </div>
                <div className={styles.moiminfo}>
                  <h1 style={{marginTop: "0.5rem", marginBottom: "0rem"}}>{moimDetailInfo.moimName}</h1>
                  <h1 style={{marginTop: "0.5rem", marginBottom: "0rem"}}>{moimDetailInfo.moimAccountNumber}</h1>
                </div>
              </div>
            </div>
          </div>
        )}

      </div>
    </div>
  );
}

export default DetailFirst;
