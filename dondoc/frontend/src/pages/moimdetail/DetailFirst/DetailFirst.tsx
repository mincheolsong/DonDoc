import styles from "./DetailFirst.module.css";
import { useState } from 'react'
import haaland from "../../../assets/bbakbbakyee.jpg"
import RequestModal from "./RequestModal/RequestModal";
import InfoUpdateModal from "./InfoupdateModal/InfoupdateModal";
import InviteModal from "./InviteModal/InviteModal";

function DetailFirst() {

  const [modalOpen, setModalOpen] = useState<boolean>(false)
  const [inviteModalOpen, setInviteModalOpen] = useState<boolean>(false)
  const [infoModalOpen, setInfoModalOpen] = useState<boolean>(false)

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
            <div className={styles.boxunit}>
              <div className={styles.unitcontent}>
                <img src={haaland} alt="" />
                <p style={{marginTop:'0rem'}}>라이스</p>
              </div>
            </div>
            <div className={styles.boxunit}>
              <img src={haaland} alt="" />
              <p>듀란</p>
            </div>
            <div className={styles.boxunit}>
              <img src={haaland} alt="" />
              <p>피터</p>
            </div>
            <div className={styles.boxunit}>
              <img src={haaland} alt="" />
              <p>제이든</p>
            </div>
            <div className={styles.boxunit}>
              <img src={haaland} alt="" />
              <p>칼리</p>
            </div>
            <div className={styles.boxunit}>
              <img src={haaland} alt="" />
              <p>루카</p>
            </div>
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
                  <p>하나</p>
                  <p>237-128127-12478</p>
                </div>
              </div>
              <div className={styles.accountowner}>
                <p>김동혁</p>
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
                <p style={{marginTop: "0.5rem", marginBottom: "0rem"}}>돈독 모임 계좌</p>
                <p style={{marginTop: "0.5rem", marginBottom: "0rem"}}>123-12847-12478</p>
              </div>
            </div>
            <div className={styles.accountbalance}>
              <div className={styles.accountwon}>
                <p style={{fontSize: "2.6rem", fontWeight:'900', border: "1px solid black", margin: "0rem"}}>100000원</p>
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
