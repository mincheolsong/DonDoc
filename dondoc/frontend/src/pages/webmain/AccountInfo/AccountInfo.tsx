import styles from "./AccountInfo.module.css";
import { BackLogoHeader } from "../../toolBox/BackLogoHeader/BackLogoHeader";
import { useLocation,useNavigate } from "react-router-dom";
import {useEffect,useState} from "react"
import { moim } from "../../../api/api";


interface createdType {
    date:string;
    at:string;
}

interface historyListType {
  afterBalance:number;
  createdAt:createdType;
  id:number;
  sign:string;
  toAccount:string;
  toSign:string;
  transferAmount:number;
  type:number;
}

interface toCodeType{
  bankCodeId : number;
  bankName : string;
}
interface memoType{
  memo:string|null;
}

type accountHistory = {
  historyId :historyListType;
  toCode:toCodeType;
  memo:memoType
}


function AccountInfo() {
  

  const navigate = useNavigate();
  const {state} = useLocation();
  const Account = state.account
  const token = state.info
  const [historyList,setHistoryList] = useState<accountHistory[]>([])
  const [firstDate,setFirstDate] = useState<string>('')

  useEffect(()=>{
    console.log(state.account.accountId) 
    
    moim.get(`/api/account/history/list/${Account.accountNumber}`,{headers:{Authorization: `Bearer ${token}`}})
    .then((response)=>{
      const data = response.data.response.historyList.response

      const newData:accountHistory[] = data.map((history)=>{
        const originalDateTimeString = history.historyId.createdAt;
        const dateObject = new Date(originalDateTimeString);
        const formattedDate = `${dateObject.getFullYear()}-${(dateObject.getMonth() + 1).toString().padStart(2, '0')}-${dateObject.getDate().toString().padStart(2, '0')}`;
        const formattedTime = `${dateObject.getHours().toString().padStart(2, '0')}:${dateObject.getMinutes().toString().padStart(2, '0')}`;
        return{
          ...history,
          historyId:{
            ...history.historyId,
            createdAt: {
              date:formattedDate,
              at:formattedTime
            }
          }

        }
      })
      const reverseData = [...newData].reverse();
      setHistoryList(reverseData)
      setFirstDate(reverseData[0].historyId.createdAt.date)
    }).catch((err)=>{
      console.log(err)
    })

  },[])
  return (
  <div style={{height:"100vh",overflow:"hidden"}}>
    
    <BackLogoHeader name={Account.accountName} fontSize="2rem" left="5rem" top="0.8rem"/>
    
    {/* Top */}
    <div style={{display:"flex", flexDirection:"column",justifyContent:"center",alignItems:"center"}} >
      <div style={{display:"flex", flexDirection:"row",justifyContent:"center", width:"100%",alignItems:"center",marginTop:"4%",fontFamily:""}}>
        <p style={{fontWeight:"bold",fontSize:"2.4rem"}}>{Account.balance} <span style={{fontSize:"2.2rem",fontFamily:"NT"}}>원</span></p>
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

    
    {/* Top */}







    {/* Mid */}
        <div style={{width:"100%"}}>
          <p style={{marginLeft:"5%",fontSize:"2rem",fontWeight:"bold",fontFamily:"NT"}}>사용내역</p>
        </div>
    

    <div style={{display:"flex", flexDirection:"column",justifyContent:"start",alignItems:'center',width:"95%", backgroundColor:"white",borderRadius:"0.8rem",height:"70vh",overflow:"sc"}}>
    
   <br />
      <div>                           
      {historyList.map((history,index)=>(
        
        <div key={index} style={{display:"flex",justifyContent:"space-between",flexDirection:"row",alignItems:"center",width:"90vw",marginTop:'5%',fontFamily:"NT"}}>
          
        <div style={{display:"flex",flexDirection:"column",justifyContent:"center",alignItems:"center"}}>
          <div style={{display:"flex", flexDirection:"row"}}>
            <img style={{marginRight:"10%"}} src={`/src/assets/Bank_Logo/${history.toCode.bankCodeId}.svg`} alt="은행로고" />
            <div style={{display:"flex",flexDirection:"column",width:"40vw", justifyContent:"center"}}>
                <p style={{margin:0,color:"#717171",fontSize:"1.2rem",fontWeight:"bold"}}>{history.historyId.createdAt.date} | {history.historyId.createdAt.at}</p>
                <p style={{margin:0,fontWeight:"bold",fontSize:"1.4rem"}}>{history.historyId.sign}</p>
            </div>
          </div>
        
          <p onClick={()=>{}} style={{fontWeight:"bold",marginBottom:"3%"}}>{history.memo ? `${history.memo}`  : ""}</p>

        </div>



        <div style={{display:"flex",flexDirection:"column",justifyContent:"center",alignItems:"end"}}>
          {history.historyId.type==1 ? <p style={{color:"#FF8282",fontWeight:"bold",margin:0,fontSize:"2rem"}}>- {history.historyId.transferAmount.toLocaleString()} 원</p>  : <p style={{color:"#267BFF",fontWeight:"bold",margin:0,fontSize:"2rem"}}>+ {history.historyId.transferAmount.toLocaleString()} 원</p> } 
          <p style={{marginTop:"3%",color:"#6B6B6B",fontWeight:"bold",fontSize:"1.2rem"}}>잔액: {history.historyId.afterBalance.toLocaleString()} 원</p>
        </div>
    </div>



      ))}
      </div>

      </div>
    </div>


    {/* Mid */}


  </div>
  );
};

export default AccountInfo;
