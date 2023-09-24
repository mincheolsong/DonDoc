import styles from "./InputBtnModal.module.css";
interface InputBtnModal{
  width : string;
  height: string;
  titleText: string;
  title: boolean;
  titleFont:string;
  contentFont: string;
  contentText: string;
  leftBtnColor: string;
  leftBtnTextColor: string;
  rightBtnColor: string;
  rightBtnTextColor: string;
  leftBtnText:string;
  rightBtnText: string;
  callback:void;
}

export function InputBtnModal(props:InputBtnModal){
  return(
    <div style={{display:"flex",justifyContent:"center", zIndex:"1"}}>
      <div className={styles.nomalBox} style={{width:props.width, height: props.height}}>
        <textarea placeholder={props.contentText} style={{fontSize:props.contentFont, width:"65%", height:"20%", border:"solid 0.2rem #E4E4E4", borderRadius:"0.5rem",resize:"none",fontWeight:"bold"}} />
        <div style={{marginTop:"8%", width:"68%", display:"flex", justifyContent:"space-between"}}>
          <button  onClick={()=>{
            props.callback
          }} className={styles.modalButton} style={{backgroundColor:props.leftBtnColor,color:props.leftBtnTextColor,fontSize:"1rem"}}>{props.leftBtnText}</button> <button className={styles.modalButton} style={{backgroundColor:props.rightBtnColor, color:props.rightBtnTextColor,fontSize:"1rem"}}>{props.rightBtnText}</button>
        </div>
      </div> 
    </div>
  )
}

export default InputBtnModal;
