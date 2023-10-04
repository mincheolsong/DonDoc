import styles from "./CallAcountItem.module.css";
import {ImCheckboxChecked} from "react-icons/im"
import {ImCheckboxUnchecked} from "react-icons/im"
import { CheckAccount } from "../../../store/slice/userSlice";
import { useState } from "react"
import { UserType } from "../../../store/slice/userSlice";
import { useSelector } from "react-redux/es/hooks/useSelector";
import { moim } from "../../../api/api";
import { useNavigate } from "react-router-dom";

type PropsType = {
  account : CheckAccount[]
}

function CallAcountItem(props:PropsType) {
  const navigate = useNavigate();

  const userInfo:UserType = useSelector((state:{user:UserType})=>{
    return state.user
  })
  const [AccountData,setAccountData] = useState(props.account)
  const accountClick = (index:number) =>{
    const updateAccount = [...AccountData];
    updateAccount[index].isCheck = !updateAccount[index].isCheck
    setAccountData(updateAccount)
  }
  const [sdeds,setsd] = useState('')
  const saveAccount = ()=>{
    const CheckedAccount = AccountData.filter((account)=>{
     return account.isCheck == true
    })
    const postSave = CheckedAccount.map((account)=>{
      return  account = {
        accountId : account.accountId,
        accountNumber: account.accountNumber,
        bankCode : account.bankCode,
        bankName : account.bankName
      }
    })
    moim.post('/api/account/account/list/save',postSave,{headers:{
      Authorization: `Bearer ${userInfo.accessToken}`
    }})
    .then((response)=>{
      // console.log(response)
      // console.log(CheckedAccount)
      navigate("/")
    })
    .catch((err)=>{
      // console.log(err)
      // console.log(postSave)
    })
  } 
  
  return (
    <div style={{textAlign:"center"}}>
        <div className={styles.mainContainer}>
        {AccountData.map((account,index)=>(
          <div key={index} className={styles.accountItem} onClick={()=>{accountClick(index)}}>
          <img className={styles.BankIcon} src={`/src/assets/Bank_Logo/${account.bankCode}.svg`} alt={account.bankCode} />
          <div style={{display:"flex",flexDirection:"column",justifyContent:"center",alignItems:"start"}}>
            <p>{account.accountName}</p>
            <p><span>{account.bankName}</span>{account.accountNumber}</p>
          </div>
          {account.isCheck ? <ImCheckboxChecked className={styles.CheckBoxU}/> : <ImCheckboxUnchecked className={styles.CheckBoxC}/>}
          
        </div>
        ))}
        </div>
        <button className={styles.bottomBtn} onClick={saveAccount}>등록하기</button>
      </div>
  );
}

export default CallAcountItem;
