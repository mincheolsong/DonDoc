import styles from "./MoimJoin.module.css";

interface Props {

}

function MoimJoin(props: Props) {
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

        <div className={styles.createcontent}>
          <div className={styles.createment}>
            <h1>모임 초대</h1>
          </div>

          <div className={styles.accountcontent}>
            <div className={styles.accountinfo}>
              <div className={styles.moimcontent}>
                <p className={styles.title}>모임이름</p>
                <p>우리는 화목해요</p>
              </div>
              <div className={styles.moimcontent}>
                <p className={styles.title}>관리자</p>
                <p>신제형</p>
              </div>
              <div className={styles.moimcontent}>
                <p className={styles.title}>연결계좌</p>
                <button className={styles.myaccountbtn}>내 계좌 목록</button>
              </div>
              <div className={styles.moimcontent}>
                <p className={styles.title}>계좌유형</p>
                <p>한명계좌</p>
              </div>
              <div className={styles.moiminfo}>
                <p className={styles.title}>모임소개</p>
                <p className={styles.moimtext}>아따마 잘 부탁드립니다.</p>
              </div>
              <div className={styles.watchtermsbtn}>
                <button className={styles.openterms}>
                  약관보기
                </button>
              </div>
              <div className={styles.checkbox}>
                <input type="checkbox" id="scales" name="sclaes" /><label htmlFor="scales">약관에 동의합니다</label>
              </div>
            </div>
          </div>

        <div className={styles.buttondiv}>
            <button className={styles.submitbutton}>모임 가입하기</button>
        </div>
        
        </div>

      </div>
    </div>
  );
};

export default MoimJoin;
