import styles from "./InputBtnModal.module.css";
import {useState} from "react"


interface InputBtnModalType{
  width : string;
  height: string;
  contentFont: string;
  contentText: string;
  leftBtnColor: string;
  leftBtnTextColor: string;
  rightBtnColor: string;
  rightBtnTextColor: string;
  leftBtnText:string;
  rightBtnText: string;
  callbackLeft():void;
  callbackRight():void;
}

export function InputBtnModal(props:InputBtnModalType){
  const [inputContent,setInputContent] = useState<string>('')

  const clickHandlerR = (item:string) =>{
    props.callbackRight(item);
  }
  const clickHandlerL = (item:string) =>{
    props.callbackLeft(item);
  }

  const inputcontent = (e)=>{
    const current = e.target.value
    setInputContent(current)
  }
  return(
    <div style={{display:"flex",justifyContent:"center", zIndex:"1", position:"fixed"}}>
      <div className={styles.nomalBox} style={{width:props.width, height: props.height}}>
        <textarea onChange={inputcontent} placeholder={props.contentText} style={{lineHeight:"6",textAlign:"center",fontSize:props.contentFont, width:"65%", height:"35%", border:"solid 0.2rem #E4E4E4", borderRadius:"0.5rem",resize:"none",fontWeight:"bold",caretColor:"white"}} />
        <div style={{marginTop:"8%", width:"68%", display:"flex", justifyContent:"space-between"}}>
          <button  onClick={()=>{
            clickHandlerL(inputContent)
          }} className={styles.modalButton} style={{backgroundColor:props.leftBtnColor,color:props.leftBtnTextColor,fontSize:"1rem"}}>{props.leftBtnText}</button> <button onClick={()=>{clickHandlerR(inputContent)}} className={styles.modalButton} style={{backgroundColor:props.rightBtnColor, color:props.rightBtnTextColor,fontSize:"1rem"}}>{props.rightBtnText}</button>
        </div>
      </div> 
    </div>
  )
}

export default InputBtnModal;
