import styles from "./TermsOfUse.module.css";


interface Props {
  setTermsOpen(id: boolean) : void
}

function TermsOfUse({setTermsOpen}: Props) {

  const TermsClose = () => {
    setTermsOpen(false)
  }

  return (
    <div className={styles.container}>
      <div className={styles.content}>

        <div className={styles.toptab}>
          <h1>이용 약관</h1>
        </div>

        <div className={styles.maincontent}>
          <div className={styles.contentbox}>

            <div className={styles.moimintro}>
              <div className={styles.introtitle}>
                <h2>이거 이용하겠다고 하면 진짜 개발자한테 항의도 못하고 그냥 그렇게 살아가는거야. 내가 개발 좀 못할 수도 있는거 아니야? 니가 입력한 데이터 좀 유출될 수도 있지 그걸 왜 나한테 따져? 그러니까 이거 동의하고 나한테 따지지마</h2>
              </div>
            </div>


          </div>
        </div>

        <div className={styles.infobtns}>
            <button onClick={TermsClose}>닫기</button>

        </div>

      </div>
    </div>
  );
}

export default TermsOfUse;
