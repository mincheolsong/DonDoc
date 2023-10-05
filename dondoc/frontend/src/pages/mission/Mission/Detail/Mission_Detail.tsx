import styles from "./Mission_Detail.module.css";
import MissionIcon from "/src/assets/MoimLogo/missionicon.svg"


function Mission_Detail() {

  return (

    <div className={styles.maincontent}>
    <div className={styles.toptab}>
      <div className={styles.requestmission}>
        <div className={styles.icon}>
          <img src={MissionIcon} alt="" />
        </div>
        <div className={styles.requesttext}>
          <p>미션등록</p>
        </div>
      </div>
    </div>


    <div className={styles.requestlist}>
      
    </div>


  </div>
  )

}

export default Mission_Detail