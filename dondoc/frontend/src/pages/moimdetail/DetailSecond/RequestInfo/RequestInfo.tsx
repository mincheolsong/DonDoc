import styles from "./RequestInfo.module.css";

type Props = {
  setInfoModalOpen(id: boolean): void
}


function RequestInfo({setInfoModalOpen}: Props) {

  const CloseInfoModal = () => {
    setInfoModalOpen(false)
  }

  return (
    <div className={styles.container}>
      <div className={styles.content}>




        <button onClick={CloseInfoModal}>닫아라</button>
      </div>
    </div>
  );
}

export default RequestInfo;
