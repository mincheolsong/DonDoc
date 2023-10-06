import styles from "./AccountListItem.module.css";
import {BiCheckboxSquare} from "react-icons/bi"
import {BiCheckbox} from "react-icons/bi"
import { CheckAccount } from "../../../store/slice/userSlice";
import { useState } from "react"
import { UserType } from "../../../store/slice/userSlice";
import { useSelector } from "react-redux/es/hooks/useSelector";
import { moim } from "../../../api/api";
import { useNavigate } from "react-router-dom";
import { useDispatch } from "react-redux";
import { changeMainAccount } from "../../../store/slice/userSlice";
import OneBtnModal from "../../toolBox/OneBtnModal";
type PropsType = {
  account : CheckAccount[]
}

function AccountListItem(props:PropsType) {
  const dispatch = useDispatch();
  const navigate = useNavigate();

  const userInfo:UserType = useSelector((state:{user:UserType})=>{
    return state.user
  })

  const [AccountData,setAccountData] = useState(props.account)
  const [acModal,setAcModal] = useState<boolean>(false)


  const accountClick = (index:number) =>{
    const updateAccount = [...AccountData];
    updateAccount[index].isCheck = true
    updateAccount.map((account,idx)=>{
      if(idx!=index){
        account.isCheck=false
      }
      return account
    })
    setAccountData(updateAccount)
  }

  const closeModal =()=>{
    setAcModal(false)
  }

  const saveAccount = ()=>{
    const CheckedAccount = AccountData.find((account)=>{
     return account.isCheck == true
    })
    if(CheckedAccount){
      moim.put('/api/account/account/main',{accountId:CheckedAccount?.accountId},{headers:{
        Authorization: `Bearer ${userInfo.accessToken}`
      }})
      .then((response)=>{
        dispatch(changeMainAccount({mainAccount:CheckedAccount?.accountId}))
        // console.log(response)
        navigate(`/mypage/${userInfo.phoneNumber}`)
      })
      .catch((err)=>{
        // console.log(CheckedAccount)
        // console.log(err)
  
      })
    }else{
      setAcModal(true)
    }
    
   
  } 
  return (
    <div style={{textAlign:"center"}}>
      {acModal ? <OneBtnModal width="90vw" height="30vh" contentText="대표계좌를 선택해주세요." contentFont="1.7rem"  btnTextColor="black" btnText="확인" callback={closeModal}/> : ""}
    <div className={styles.mainContainer}>
    {AccountData.map((account,index)=>(
      <div key={index} className={styles.accountItem} onClick={()=>{accountClick(index)}}>
      <img className={styles.BankIcon} src={`/src/assets/Bank_Logo/${account.bankCode}.svg`} alt={account.bankCode} />
      <div style={{display:"flex",flexDirection:"column",justifyContent:"center",alignItems:"start",width:"55vw"}}>
        <p style={{margin:"0",marginBottom:"0.5rem",color:"#6C6C6C",fontWeight:"bold",fontSize:"1.2rem"}}>{account.accountName}</p>
        <p style={{margin:"0",fontWeight:"bold",fontSize:"1.3rem"}}><span>{account.bankName}</span>{account.accountNumber}</p>
      </div>
      {account.isCheck ? <BiCheckboxSquare className={styles.CheckBoxU}/> : <BiCheckbox className={styles.CheckBoxC}/>}
      
    </div>
    ))}
    </div>
    <button className={styles.bottomBtn} onClick={saveAccount}>등록하기</button>
  </div>
  );
};

export default AccountListItem;
