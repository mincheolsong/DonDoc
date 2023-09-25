import styles from "./MoimHome.module.css";
import haaland from '../../../assets/bbakbbakyee.jpg'
import peter from "../../../assets/image/peter.svg"
import Header from "../../webmain/Header/Header";
import chelsea from '../../../assets/Chelsea_FC_Logo.jpg'
import { useNavigate } from "react-router-dom";

function MoimHome() {

  return (
    <div className={styles.container}>
      <div className={styles.content}>

        <Header />
        <UserBox userCharacter={peter} username="Jaden" rightBtn="모임 생성"/>

        <div className={styles.moimlist}>
          <div className={styles.moimlisttitle}>
            <h1>나의 모임</h1>
          </div>

          <div className={styles.moimcontent}>
            <h1 className={styles.moimunit}>캐러셀해라 노예야 노예야</h1>
            <h1 className={styles.moimunit}>캐러셀해라 노예야 노예야</h1>
            <h1 className={styles.moimunit}>캐러셀해라 노예야 노예야</h1>
            <h1 className={styles.moimunit}>캐러셀해라 노예야 노예야</h1>
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
    </div>
  );
}

export default MoimHome;


export function UserBox(props){
  const navigate = useNavigate()
  const ToCreateMoim = () => {
    navigate('/createmoim')
  }

  return(
    <div className={styles.topContainer}>
      <div style={{display:"flex",width:"60%"}}>
        <img src={props.userCharacter} style={{width:"35%"}} />
        <div style={{marginLeft:"1rem",textAlign:"center"}}>
          <p style={{fontSize:"1.2rem",fontWeight:"bold"}}>{props.username} 의 DonDoc</p>
          <button className={styles.myProfileBtn} onClick={()=>{
            navigate("/mypage")
            }}> 나의프로필가기</button>
        </div>
      </div>
      
      <div>
        <button className={styles.myProfileBtn} style={{height:"5rem",fontSize:"1.2rem"}} onClick={ToCreateMoim} > {props.rightBtn}</button>
      </div>
    </div>

  )

}
