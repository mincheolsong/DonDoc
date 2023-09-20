import styles from "./DetailFirst.module.css";
import haaland from "../../../assets/bbakbbakyee.jpg"

function DetailFirst() {
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
        </div>

        <div className={styles.userscontent}>
          
          <div className={styles.usersbox}>
            <div className={styles.boxunit}>
              <img src={haaland} alt="" />
              <p style={{marginTop:'0rem'}}>라이스</p>
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
              <button className={styles.invbtn}>+ 초대하기</button>
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
              <button>요청하기</button> <button>요청확인</button>
            </div>
          </div>

        </div>

        <div className={styles.moimaccountcontent}>
          <div className={styles.moimaccountbox}>
            <div className={styles.moimaccountinfo}>
              <div className={styles.moimbanklogo}>
                Logo
              </div>
              <div className={styles.moiminfo}>
                <p>돈독 모임 계좌</p>
                <p>123-12847-12478</p>
              </div>
            </div>
            <div className={styles.accountbalance}>
              <div className={styles.accountwon}>
                <p style={{fontSize: "2rem",}}>100000원</p>
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
