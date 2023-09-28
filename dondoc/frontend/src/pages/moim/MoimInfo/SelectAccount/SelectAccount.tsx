import styles from "./SelectAccount.module.css";

interface Props {
  setSelectAccountOpen(id: boolean) : void
}

function SelectAccount({setSelectAccountOpen}: Props) {
  const SelectAccountClose = () => {
    setSelectAccountOpen(false)
  }

  return (
    <div className={styles.container}>
      <div className={styles.content}>

        <div className={styles.toptab}>
          <h1>내가 연결할 계좌 선택</h1>
        </div>

        <div className={styles.maincontent}>
          <div className={styles.contentbox}>

            <div className={styles.moimintro}>
              <div className={styles.introtitle}>
                <h2></h2>
              </div>
            </div>


          </div>
        </div>

        <div className={styles.infobtns}>
            <button onClick={SelectAccountClose}>닫기</button>

        </div>

      </div>
    </div>
  );
}

export default SelectAccount;
