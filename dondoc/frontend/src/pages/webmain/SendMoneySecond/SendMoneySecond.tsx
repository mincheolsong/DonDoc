import styles from "./SendMoneySecond.module.css";
import BackLogoHeader from "../../toolBox/BackLogoHeader";
import hana from "../../../assets/image/hana.svg"
import { AiFillCheckSquare } from 'react-icons/ai';
import { useNavigate,useLocation } from "react-router-dom";
import {useEffect ,useState} from 'react'


function SendMoneySecond() {
  
  const naviate = useNavigate();
  const {state} = useLocation();
  const [toTransferAmount,setToTransferAmount] = useState<number|string>(0)
  const transC = (e:React.ChangeEvent<HTMLInputElement>) =>{
    const value: string = e.target.value;
    const removedCommaValue: number = Number(value.replaceAll(",", ""));
    setToTransferAmount(removedCommaValue.toLocaleString());
  }
  useEffect(()=>{
    console.log(state)
  },[])
  return (
    <div style={{height:"100vh"}}>
      <BackLogoHeader name="송금하기" left="5rem" fontSize="2rem" top="0.8rem" />
      <div className={styles.topContainer}>
      <div className={styles.accountItem}>
      <div className={styles.topItem}>
        <img src={`/src/assets/Bank_Logo/${state.myAccount.account.bankCode}.svg`} style={{marginLeft:"2rem",marginRight:"10%"}} />
        <div className={styles.topItemInner}>
            <p style={{margin:"0px",fontWeight:"bold",marginTop:'1.7rem',fontSize:"1.4rem"}}> {state.myAccount.account.accountName}</p>
            <p style={{margin:"0px",color:"#626262",fontSize:"1.1rem",fontWeight:"bold"}}> {state.myAccount.account.accountNumber}</p>
            <p style={{margin:"0px",textAlign:"end",fontWeight:"bold",marginRight:"1rem"}}> {state.myAccount.account.balance}</p>
        </div>
      </div>
  
      <div className={styles.botItem}>
      <img src={`/src/assets/Bank_Logo/${state.toAccount.toCode}.svg`} style={{marginLeft:"2rem",marginRight:"10%"}} />
          <div className={styles.botItemInner}>
              
              <p style={{margin:"0px",fontWeight:"bold",marginTop:'1.7rem',fontSize:"1.4rem"}}> {state.toAccount.sign}<AiFillCheckSquare color="#00D26A"/> </p>
              <p style={{margin:"0px",color:"#626262",fontSize:"1.1rem",fontWeight:"bold"}}> {state.toAccount.toAccount}</p>
          
          </div>
      </div>

      <div style={{marginTop:"1.5rem",width:"90%",display:"flex",flexDirection:"column",justifyContent:"center",alignItems:"center"}}>
        <p style={{fontWeight:"bold", fontSize:"1.5rem"}}>얼마를 보낼까요?</p> 
        <div style={{display:"flex",alignItems:"center"}}>
        <input onChange={transC} style={{textAlign:"center"}} type="text" value={toTransferAmount} className={styles.inputBox} />
        <span className={styles.won}>원</span>
        </div>
        <button className={styles.confirmBtn}>확인</button>
      </div>



    </div>  

      </div>
      
      
    </div>
  );
}

export default SendMoneySecond;

