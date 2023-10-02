import styles from "./AccountInfo.module.css";
import { BackLogoHeader } from "../../toolBox/BackLogoHeader/BackLogoHeader";
import { useLocation,useNavigate } from "react-router-dom";
import {useEffect} from "react"
interface BackLogoHeader{
  left : string;
  name : string;
  fontSize: string;
  top :string; 
}



function AccountInfo() {
  const navigate = useNavigate();
  const {state} = useLocation();
  const Account = state.account
  useEffect(()=>{
  },[])
  return (
  <div>
    
    <BackLogoHeader name={Account.accountName} fontSize="2rem" left="5rem" top="0.8rem"/>
    
    {/* Top */}
    <div style={{display:"flex", flexDirection:"column",justifyContent:"center",alignItems:"center"}} >
      <div style={{display:"flex", flexDirection:"row",justifyContent:"center", width:"100%",alignItems:"center"}}>
        <p style={{fontWeight:"bold",fontSize:"2.4rem"}}>{Account.balance} 원</p>
        <button className={styles.sendMoneyBtn} onClick={()=>{
          navigate(`/sendmoneyfirst/${Account.accountNumber}`,{state:{account:Account}})
        }}>송금</button>
      </div>

      <div className={styles.accountBox}>
        <img src={`/src/assets/Bank_Logo/${Account.bankCode}.svg`} alt="" />
        <div style={{display:"flex",flexDirection:"column"}}>
          <p style={{margin:"0",color:"#6C6C6C"}}>{Account.accountName}</p>
          <p style={{margin:"0"}}>{Account.accountNumber}</p>
        </div>
      </div>

      <div className={styles.connected}>
         연결된 모임보기
      </div>

    </div>
    {/* Top */}







    {/* Mid */}

    <p>사용내역</p>


    <div style={{display:"flex", flexDirection:"column",justifyContent:"center",alignItems:'center',width:"98%", backgroundColor:"white"}}>
      <div style={{display:"flex", flexDirection:"row", justifyContent:"space-between",width:"95%"}}>
        <p>사용월</p>
        <button>열기접기</button>
       
      </div>
      
      
      
      <div>
          여기는 map 함수로 
      </div>


    </div>


    {/* Mid */}


  </div>
  );
};

export default AccountInfo;
