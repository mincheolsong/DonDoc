import styles from "./MoimLinkAccount.module.css";
import ssafylogo from '../../../assets/ssafy_logo.png'

function MoimLinkAccount() {
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
            <h3>모임통장 생성</h3>
          </div>
        </div>

        <div className={styles.createcontent}>
          <div className={styles.createment}>
            <h1>연결할 계좌를 선택해주세요.</h1>
          </div>

          <div className={styles.accounts}>
            <div className={styles.accountunit}>
              <div className={styles.banklogo}>
                <img src={ssafylogo} alt="" className={styles.ssafylogo}/>
              </div>
              <div className={styles.accountinfo}>
                <p>대구은행 영플러스통장</p>
                <p className={styles.accountnumber}>대구은행 237-128127-12478</p>
              </div>
              <div className={styles.selectcount}>
                <input type="radio" name="contact" value="select" />
              </div>
            </div>
          </div>

        <div className={styles.buttondiv}>
            <button className={styles.submitbutton}>다음</button>
        </div>
        
        </div>


      </div>
    </div>
  );
}

export default MoimLinkAccount;
