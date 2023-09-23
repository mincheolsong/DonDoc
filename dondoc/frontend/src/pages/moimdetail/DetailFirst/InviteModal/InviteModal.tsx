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



      </div>
    </div>
  );
};

export default InviteModal;
