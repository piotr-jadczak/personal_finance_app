import './moneyDisplayer.css'


const MoneyDisplayer = (props) => {

  const money = Math.round(props.money * 100) / 100
  const description = props.description
  return (
    <div>
      <div className="total-money">
        <br />
        <a> {money + " zł"}</a>
      </div>
      <div className="total_underscore">
        <hr style={{margin: 0}} />
        {description}
      </div>
    </div>
  )
}

export default MoneyDisplayer