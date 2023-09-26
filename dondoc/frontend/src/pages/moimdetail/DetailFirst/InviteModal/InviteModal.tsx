import {useState} from "react";
import styles from "./InviteModal.module.css";

interface Props {
  setModalOpen(id: boolean) : void
}

function InviteModal({setModalOpen}: Props) {
  const [nowSelected, setNowSelected] = useState<boolean>(true)

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

  return (
    <div className={styles.container}>
      <div className={styles.content}>

        <div className={styles.toptab}>
          <div className={styles.requesttext}>
            <p style={{color: nowSelected ? '#7677E8' : '', borderBottom: nowSelected ? '4px solid #7677E8' : ''}}>초대하기</p>
          </div>
        </div>

        <div className={styles.maincontent}>
          <div className={styles.searchbox}>
            <div className={styles.inputgroup}>
              <div className={styles.inputlabel}>
                <h2>전화번호, 이름</h2>
              </div>
              <div className={styles.inputbox}>
                <input type="text" />
              </div>
            </div>
          </div>
          
          <div className={styles.searchresult}>
          
          </div>

          <div className={styles.friendlist}>
            <div className={styles.friendcontent}>  
              <div className={styles.listlabel}>
                <h2>친구 리스트</h2>
              </div>
              <div className={styles.friendbox}>
                
              </div>
            </div>
          </div>

          <div className={styles.invitelist}>
            <div className={styles.invitecontent}>  
              <div className={styles.listlabel}>
                <h2>초대 리스트</h2>
              </div>
              <div className={styles.invitebox}>
                
              </div>
            </div>
          </div>

        </div>

        <div className={styles.infobtns}>
            <button onClick={ModalClose}>닫기</button>
            <button>수정하기</button>
        </div>

      </div>
    </div>
  );
};

export default InviteModal;
