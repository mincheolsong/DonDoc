import { BackLogoHeader } from "../../toolBox/BackLogoHeader/BackLogoHeader";
import styles from "./SendMoneyFirst.module.css";
import hana from "../../../assets/image/hana.svg"
import searchIcon from "../../../assets/image/search.svg"
import {useState,useEffect} from "react"
import SelectIcon from "../../toolBox/SelectIcon";
import { useNavigate,useLocation } from "react-router-dom";
import { moim } from "../../../api/api";
import { UserType } from "../../../store/slice/userSlice";
import { useSelector } from "react-redux/es/hooks/useSelector";
import OneBtnModal from "../../toolBox/OneBtnModal";
interface sendMoneyAccount{
  accountId: string|null;
  password:string|null;
  sign:string|null;
  toAccount:string|null;
  toCode:string|null;
  toSign:string|null;
  transferAmount:number|null;
}

function SendMoneyFirst() {
  const naviate = useNavigate();
  const {state} = useLocation();
  const Account = state.account
  const [toAccountSetting,setToAccount] = useState<sendMoneyAccount>();
  const [iconModal,setIconModal] = useState<boolean>(false);
  const [iconN,setIconN] = useState<string>('');
  const [toAccountNumber,setToAccountNumber] = useState<string>('');
  const [secondSuccessModal,setSecondSuccessModal] = useState<boolean>(false);
  const [failText,setFailText] = useState<string>('')
  const userInfo:UserType = useSelector((state:{user:UserType})=>{
    return state.user
  })
  // const IconClick = 
  // "accountId": 6,
  // "password": 1234,
  // "sign": "내 계좌에 남는 표시",
  // "toAccount": 8374837098285,
  // "toCode": 4,
  // "toSign": "상대 계좌에 남는 표시",
  // "transferAmount": 100000
  const accountC = (e:React.ChangeEvent<HTMLInputElement>) =>{
    const toac = e.target.value
    setToAccountNumber(toac)
  
  }
  const iconC = (code:string)=>{
    setIconN(code);
    setIconModal(!iconModal)
  }
  useEffect(()=>{
    
    const AccountTo = 
      {
        accountId: Account.accountId,
        password: null,
        sign:null,
        toAccount:null,
        toCode:null,
        toSign:Account.accountName,
        transferAmount:null,
      }
    
    setToAccount(AccountTo)
  },[])


  const modalclose = ()=>{
    setSecondSuccessModal(false)
  }


  const goSendMoneySecond = ()=>{
    
   
    const checkId = {
      accountNumber:toAccountNumber,
      bankCode: iconN
    }
    moim.post(`/api/account/account/certification`,checkId,{headers:{
      Authorization: `Bearer ${userInfo.accessToken}`
    }})
    .then((response)=>{
      if(response.data.success == true){
        const newSecondAccount = {...toAccountSetting,
          toAccount : toAccountNumber,
          toCode : iconN,
          sign: response.data.response.response.ownerName
        }
        naviate(`/sendmoneysecond/${toAccountSetting?.accountId}`,{state:{myAccount:state,toAccount:newSecondAccount}})
      }else{
        setFailText(response.data.error.message)
        setSecondSuccessModal(true)
      }
    })
    .catch((err)=>{
      console.log(err)
    })


    
  }


  return (
    <div className={styles.container}>
      <BackLogoHeader name="송금하기" left="5rem" fontSize="2rem" top="0.8rem" />
      <div className={styles.midContainer}>
      {secondSuccessModal ? <OneBtnModal width="80vw" height="30vh" title={false} titleText="" contentFont="1.3rem" contentText={failText} btncolor="white" btnTextColor="black" btnText="닫기" callback={modalclose} /> : ''}
        <p style={{fontSize:"2.7rem", fontWeight:"bold",marginTop:"3rem"}}>
          누구에게 송금할까요? 
        </p>
{/* 은행코드 이미지에 따라 스타일 해서 불러오고 없을때는 텍스트 띄워주기 */}
{/* 모달로 은행코드 넣는거 가져오기  */}
        <div className={styles.itemBox1}>
          {iconN ? <button onClick={()=>{
          setIconModal(!iconModal)
         }} className={styles.bankBtn}> <img style={{fontWeight:"bold"}} src={`/src/assets/Bank_Logo/${iconN}.svg`} alt="은행코드" /> </button>  :  <button onClick={()=>{
          setIconModal(!iconModal)
         }} className={styles.bankBtn}> <img style={{fontWeight:"bold"}} src={`/src/assets/image/whiteBox.svg`} alt="은행코드" /></button> }
        
         <input onChange={accountC} placeholder="계좌번호를 입력해주세요." className={styles.inputBox} type="text" />
        </div>
        <button onClick={goSendMoneySecond} className={styles.confirmBtn}>확인</button>
      </div>
      


      <p style={{fontSize:"2.4rem",fontWeight:"bold",marginLeft:"1rem",marginBottom:"0.4rem"}}>친구목록에서 보내기</p>

         {iconModal ? <SelectIcon iconClick={iconC}/> : ' '} 
         


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
