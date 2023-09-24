import {useState} from "react";
import styles from "./InfoupdateModal.module.css";

interface Props {
  setModalOpen(id: boolean) : void
}

function InfoupdateModal({setModalOpen}: Props) {
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
          <h1>올해는 다이어트 성공</h1>
        </div>

        <div className={styles.maincontent}>
          <div className={styles.contentbox}>

            <div className={styles.moimintro}>
              <div className={styles.introtitle}>
                <h2>모임 소개</h2>
              </div>
              <div className={styles.introcontent}>
                <textarea className={styles.introinput}></textarea>
              </div>
            </div>

            <div className={styles.moimmembers}>
              <div className={styles.introtitle}>
                <h2>멤버</h2>
              </div>
              <div className={styles.memberscontent}>

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
  )
};

export default InfoupdateModal;
