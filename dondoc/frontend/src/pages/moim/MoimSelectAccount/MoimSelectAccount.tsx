import styles from "./MoimSelectAccount.module.css";


function MoimSelectAccount() {
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
            <h1>계좌 유형을 선택해주세요.</h1>
          </div>

          <div className={styles.accountscategory}>
            <div className={styles.categoryinfo}>
              <div className={styles.accountinfo}>
                <p>한명 관리 계좌는 관리자 한명이 계좌의 이체권한을 가지는 모임통장 형식입니다</p>
              </div>

              <div className={styles.selectcategories}>
                <div className={styles.selectunit}>
                  <label htmlFor="">한명 관리</label>
                  <input type="radio" name="contact" value="select" />
                </div>
                <div className={styles.selectunit}>
                  <label htmlFor="">두명 관리</label>
                  <input type="radio" name="contact" value="select" />
                </div>
                <div className={styles.selectunit}>
                  <label htmlFor="">공동 관리</label>
                  <input type="radio" name="contact" value="select" />
                </div>
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

export default MoimSelectAccount;
