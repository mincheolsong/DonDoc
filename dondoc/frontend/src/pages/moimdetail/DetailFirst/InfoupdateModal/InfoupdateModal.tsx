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

      </div>
    </div>
  )
};

export default InfoupdateModal;
