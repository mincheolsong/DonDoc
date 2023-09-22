import styles from "./MoimHome.module.css";
import haaland from '../../../assets/bbakbbakyee.jpg'
import chelsea from '../../../assets/Chelsea_FC_Logo.jpg'
import { useNavigate } from "react-router-dom";

function MoimHome() {

  const navigate = useNavigate()
  const ToCreateMoim = () => {
    navigate('/createmoim')
  }


  return (
    <div className={styles.container}>
      <div className={styles.content}>

        <div className={styles.topbar}>
          <div className={styles.Logo}>
            <p>Dondoc Logo</p>
          </div>
          <div className={styles.notice}>
            <p>Bell Icon</p>
          </div>
        </div>

        <div className={styles.mytap}>
          <div className={styles.character}>
            <img src={haaland} alt="" className={styles.haaland}/>
          </div>
          <div className={styles.toprofile}>
            <p>동혁시치의 DonDoc</p>
            <button className={styles.goprofile}>내 프로필</button>
          </div>
          <div className={styles.makemoim}>
            <button className={styles.createmoimbtn} onClick={ToCreateMoim}>모임 생성</button>
          </div>
        </div>

        <div className={styles.moimlist}>
          <div className={styles.moimlisttitle}>
            <h1>나의 모임</h1>
          </div>

          <div className={styles.moimcontent}>
            <h1 className={styles.moimunit}>캐러셀해라 노예야 노예야</h1>
            <h1 className={styles.moimunit}>캐러셀해라 노예야 노예야</h1>
            <h1 className={styles.moimunit}>캐러셀해라 노예야 노예야</h1>
          </div>

        </div>

        <div className={styles.invitelist}>
          <div className={styles.inviteisttitle}>
            <h1>초대현황</h1>
          </div>

          <div className={styles.invitecontent}>
            <div className={styles.invitebox}>
              <div className={styles.inviteunit}>
                <div className={styles.linkbank}>
                <img src={chelsea} className={styles.chelsea} alt="" />
                </div>
                <h4 className={styles.invitemessage}>아오아오주연주연시치가 아오동혁시치시치를 행복한 첼시에 초대하였습니다.</h4>
              </div>
            </div>
          </div>

        </div>

      </div>
    </div>
  );
}

export default MoimHome;
