import styles from "./CreateMoim.module.css";

function CreateMoim() {
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
            <h1>모임 통장을 만들어 보아요.</h1>
          </div>

          <div className={styles.inputs}>
            <div className={styles.moimname}>
              <textarea className={styles.inputbox} placeholder="모임 이름을 입력해 주세요"></textarea>
            </div>
            <div className={styles.moininfo}>
              <textarea className={styles.inputbox} placeholder="모임을 소개해 주세요&#13;&#10;초대되는 인원들이 보게 되요!"
               rows={5}></textarea>
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

export default CreateMoim;
