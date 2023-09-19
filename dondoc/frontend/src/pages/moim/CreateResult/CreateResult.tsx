import styles from "./CreateResult.module.css";

interface Props {

}

function CreateResult(props: Props) {
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
            <h2>모임 개설준비가 완료 되었습니다</h2>
          </div>

          <div className={styles.accountcontent}>
            <div className={styles.accountinfo}>
              <div className={styles.moimcontent}>
                <p className={styles.title}>모임이름</p>
                <p>우리는 화목해요</p>
              </div>
              <div className={styles.moimcontent}>
                <p className={styles.title}>연결계좌</p>
                <p>농협 어쩌고저쩌고계좌번호</p>
              </div>
              <div className={styles.moimcontent}>
                <p className={styles.title}>계좌유형</p>
                <p>한명 계좌</p>
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
            <button className={styles.submitbutton}>계좌 개설하기</button>
        </div>
        
        </div>

      </div>
    </div>
  );
}

export default CreateResult;
