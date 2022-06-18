import './currency.css'
import { BsXSquare } from 'react-icons/bs'
import { BsPencilFill } from 'react-icons/bs'

const Currency = (props) => {

  const id = props.curr.id;
  const quantity = props.curr.quantity;
  const avgBought = props.curr.avgBought;
  const currentPrice = props.curr.currencyData.currentPrice;
  const symbol = props.curr.currencyData.symbol;
  const currencyName = props.curr.currencyData.currencyName;
  const fetchTime = props.curr.currencyData.fetchTime;
  const profit = Math.round((currentPrice-avgBought) * quantity * 100) / 100;

  const textClass = () => {
    if(profit > 0)
      return "text-success no-"
    if(profit < 0)
      return "text-danger"
    return "text-secondary"    
  }

  const onDeleteButtonClick = () => {
    props.onDelete(id)
  }

  const onUpdateButtonClick = () => {
    props.onUpdateForm(id, {quantity : quantity, avgBought : avgBought, currencyName : currencyName })
  }

  return (
    <div className="col-sm-3">
      <div className='currency'>
        <div className='curr-buttons-holder'>
          <BsPencilFill size={20} className='curr-edit-button' onClick={onUpdateButtonClick} />
          <BsXSquare size={24} className='curr-delete-button' onClick={onDeleteButtonClick} />
        </div>
        <a className='currency-price'>{currentPrice + "zł"}</a><br/>
        <a className='currency-name'>{currencyName + " (" + symbol + ")" }</a><br/><br/>
        <a>{avgBought+ " zł"}</a><br/>
        <a className='min-descriptor'>avg bougth</a><br/>
        <a>{quantity}</a><br/>
        <a className='min-descriptor'>quantity</a><br/>
        <a className={textClass() + ' text-decoration-none'}>{profit+ " zł"}</a><br/>
        <a className='min-descriptor'>profit</a>
        <div className='curr-time-holder'>
          <a className='curr-fetch-time'>{ fetchTime.slice(0, -3) }</a>
        </div>
      </div>
    </div>
  )
}

export default Currency