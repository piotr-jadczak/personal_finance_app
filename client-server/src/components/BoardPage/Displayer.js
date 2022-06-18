import './Displayer.css'

const Displayer = (props) => {

  const description = props.description
  const money = props.money

  return (
    <div>
    <div className="displayer-money">
      <br />
      <a> {money}</a>
    </div>
    <div className="displayer-underscore">
      <hr style={{margin: 0}} />
      <a>{description}</a>
    </div>
  </div>
  )
}

export default Displayer