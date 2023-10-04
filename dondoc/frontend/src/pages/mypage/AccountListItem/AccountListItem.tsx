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


  const saveAccount = ()=>{
    const CheckedAccount = AccountData.find((account)=>{
     return account.isCheck == true
    })
   
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
      {account.isCheck ? <BiCheckboxSquare className={styles.CheckBoxU}/> : <BiCheckbox className={styles.CheckBoxC}/>}
      
    </div>
    ))}
    </div>
    <button className={styles.bottomBtn} onClick={saveAccount}>등록하기</button>
  </div>
  );
};

export default AccountListItem;
