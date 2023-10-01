import styles from "./SelectIcon.module.css";
import { Bank_List } from "../../../constants";

function SelectIcon(props) {
  // 이벤트 부모컴포넌트로 올려주는거 해야함 . 기상해서 할것 . 

  const clickHandler = (code:string) =>{
    props.iconClick(code);
  }
  return (
    <div className={styles.IconBox}>
        {Bank_List.map((bank,index)=>(
            <div onClick={()=>{clickHandler(bank.code)}} className={styles.Icon}>
            <img src={`/src/assets/Bank_Logo/${bank.code}.svg`} alt="" />
            <p>{bank.bank}</p>
            </div>
        ))}
    </div>
  );
};

export default SelectIcon;
