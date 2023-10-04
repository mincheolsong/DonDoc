import styles from "./TowBtnModal.module.css";


interface TowBtnModal{
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
  callbackLeft():void;
  callbackRight():void;
}

export function TowBtnModal(props:TowBtnModal){
  const clickHandlerR = () =>{
    props.callbackRight();
  }
  const clickHandlerL = () =>{
    props.callbackLeft();
  }

  return(
    <div style={{display:"flex",justifyContent:"center", zIndex:"2",position:"fixed",top:"15%"}}>
      <div className={styles.nomalBox} style={{width:props.width, height: props.height}}>
       {props.title ? <p  style={{fontSize:props.titleFont, marginTop:"0" ,fontWeight:"bold"}}>{props.titleText}</p> : ''}
        <p  style={{fontSize:props.contentFont, fontWeight:"bold"}}>{props.contentText}</p>
        <div style={{marginTop:"8%", width:"68%", display:"flex", justifyContent:"space-between"}}>
          <button 
           onClick={()=>{
            clickHandlerL()
          }}
          className={styles.modalButton} style={{backgroundColor:props.leftBtnColor,color:props.leftBtnTextColor,fontSize:"1rem"}}>{props.leftBtnText}</button> 
          
          <button
           onClick={()=>{
            clickHandlerR()
          }}
          className={styles.modalButton} style={{backgroundColor:props.rightBtnColor, color:props.rightBtnTextColor,fontSize:"1rem"}}>{props.rightBtnText}</button>
        </div>
      </div> 
    </div>
  )
}


export default TowBtnModal;
