import styles from "./CallAcountItem.module.css";
import { FaBeer } from 'react-icons/fa';
import {ImCheckboxChecked} from "react-icons/im"
import {ImCheckboxUnchecked} from "react-icons/im"

interface AccountItem{
  bankCode:string,
  accountName:string,
  bankName:string,
  accountNumber:number,
  checked:boolean
}

function CallAcountItem(props:AccountItem) {
  return (
          <div className={styles.accountItem}>
            <img className={styles.BankIcon} src={props.bankCode} alt="" />
            <div style={{display:"flex",flexDirection:"column",justifyContent:"center",alignItems:"start"}}>
              <p>{props.accountName}</p>
              <p><span>{props.bankName}</span>{props.accountNumber}</p>
            </div>
            {props.checked ? <ImCheckboxChecked className={styles.CheckBoxU}/> : <ImCheckboxUnchecked className={styles.CheckBoxC}/>}
  
          </div>
  );
}

export default CallAcountItem;
