import styles from "./SendMoneySecond.module.css";
import BackLogoHeader from "../../toolBox/BackLogoHeader";
import hana from "../../../assets/image/hana.svg"
import { AiFillCheckSquare } from 'react-icons/ai';


function SendMoneySecond() {
  return (
    <div style={{height:"100vh"}}>
      <BackLogoHeader name="송금하기" left="5rem" fontSize="2rem" top="0.8rem" />
      <div className={styles.topContainer}>
      <AccountCopy/>

      </div>
      
      
    </div>
  );
}

export default SendMoneySecond;



 export function AccountCopy(){
  return(
    <div className={styles.accountItem}>
      <div className={styles.topItem}>
        <img src={hana} style={{marginLeft:"2rem"}} />
        <div className={styles.topItemInner}>
            <p style={{margin:"0px",fontWeight:"bold",marginTop:'1.7rem',fontSize:"1.4rem"}}> 하나은행 주거래 통장</p>
            <p style={{margin:"0px",color:"#626262",fontSize:"1.1rem",fontWeight:"bold"}}> 계좌번호 1231231221</p>
            <p style={{margin:"0px",textAlign:"end",fontWeight:"bold",marginRight:"1rem"}}>잔액 :1230원</p>
        </div>
      </div>
  
      <div className={styles.botItem}>
      <img src={hana} style={{marginLeft:"2rem"}} />
          <div className={styles.botItemInner}>
              
              <p style={{margin:"0px",fontWeight:"bold",marginTop:'1.7rem',fontSize:"1.4rem"}}> 받는사람 이름 <AiFillCheckSquare color="#00D26A"/> </p>
              <p style={{margin:"0px",color:"#626262",fontSize:"1.1rem",fontWeight:"bold"}}> 계좌번호 1231231221</p>
              <p style={{margin:"0px",textAlign:"end",fontWeight:"bold",marginRight:"1rem"}}>잔액 :1230원</p>
          </div>
      </div>

      <div style={{marginTop:"1.5rem",width:"90%",display:"flex",flexDirection:"column",justifyContent:"center",alignItems:"center"}}>
        <p style={{fontWeight:"bold", fontSize:"1.5rem"}}>얼마를 보낼까요?</p>
        <input type="text" className={styles.inputBox} />

        <button className={styles.confirmBtn}>확인</button>
      </div>



    </div>  
  )
 }

