import styles from "./SelectIcon.module.css";
import { Bank_List } from "../../../constants";

function SelectIcon(props) {

  const clickHandler = (code:string) =>{
    props.iconClick(code);
  }

  return (
    <div className={styles.IconBox}>
        {Bank_List.map((bank,index)=>(
            <div key={index} onClick={()=>{clickHandler(bank.code)}} className={styles.Icon}>
            <img style={{width:"30%"}} src={`/src/assets/Bank_Logo/${bank.code}.svg`} alt="" />
            <p style={{fontFamily:"NT",fontSize:"1.5rem",margin:"2%",}}>{bank.bank}</p>
            </div>
        ))}
    </div>
  );
};

export default SelectIcon;
