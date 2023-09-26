import { BackLogoHeader } from "../../toolBox/BackLogoHeader/BackLogoHeader";
import styles from "./SendMoneyFirst.module.css";
import hana from "../../../assets/image/hana.svg"
import searchIcon from "../../../assets/image/search.svg"

function SendMoneyFirst() {
  return (
    <div className={styles.container}>
      <BackLogoHeader name="송금하기" left="5rem" fontSize="2rem" top="0.8rem" />
      <div className={styles.midContainer}>
        <p style={{fontSize:"2.7rem", fontWeight:"bold",marginTop:"3rem"}}>
          누구에게 송금할까요? 
        </p>
{/* 은행코드 이미지에 따라 스타일 해서 불러오고 없을때는 텍스트 띄워주기 */}
{/* 모달로 은행코드 넣는거 가져오기  */}
        <div className={styles.itemBox1}>
         <button className={styles.bankBtn}> <img src={hana} alt="" /> </button> 
         <input placeholder="계좌번호를 입력해주세요." className={styles.inputBox} type="text" />
        </div>
        <button className={styles.confirmBtn}>확인</button>
      </div>
      


      <p style={{fontSize:"2.4rem",fontWeight:"bold",marginLeft:"1rem",marginBottom:"0.4rem"}}>친구목록에서 보내기</p>

      <div className={styles.bottomBox}>

      <div className={styles.bottomContainer}>
        <div style={{display:"flex", flexDirection:"row",justifyContent:"center",alignItems:"center",marginTop:"1rem"}}>
        <input className={styles.searchBox} type="text" />
         <img src={searchIcon} style={{width:"3rem"}} />
        </div>
         <hr style={{width:"90%", marginTop:"2rem"}} />
        
        
        {/*  여기 .map 으로 계좌 불러오기  */}
        <div style={{width:"100%",overflow:"scroll"}}> 
          <div className={styles.friendAccount} >
            <img style={{width:"6rem"}} src={hana} alt="" />
            <div>
              <p style={{fontWeight:"bold",fontSize:"1.7rem",margin:"0.2rem"}}>신제형</p>
              <p style={{fontWeight:"bold",fontSize:"1.3rem",color:"#A4A4A4",margin:"0.2rem"}}>182391284923817498237489</p>
            </div>
          </div>

          
        </div>

      </div>
       
      </div>
      
    
    </div>
  );
}

export default SendMoneyFirst;
